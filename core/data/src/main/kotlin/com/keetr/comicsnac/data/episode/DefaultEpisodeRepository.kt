package com.keetr.comicsnac.data.episode

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.episode.Episode
import com.keetr.comicsnac.model.episode.EpisodeDetails
import com.keetr.comicsnac.network.episode.EpisodeNetworkSource
import com.keetr.comicsnac.network.episode.models.EpisodeListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultEpisodeRepository @Inject constructor(
    private val networkSource: EpisodeNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : EpisodeRepository {
    override fun getEpisodeDetails(id: String): Flow<RepositoryResponse<EpisodeDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getEpisodeDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toEpisodeDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getEpisodesWithId(episodesId: List<Int>): Flow<PagingData<Episode>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getEpisodesWithId(
                            apiKey,
                            PageSize,
                            PageSize * page,
                            episodesId
                        ).getOrThrow().results
                    }, mapper = List<EpisodeListApiModel>::toEpisodes
                )
            }.flow
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllEpisodes(): Flow<PagingData<Episode>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllEpisodes(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    }, mapper = List<EpisodeListApiModel>::toEpisodes
                )
            }.flow
        }.flowOn(dispatcher)

    companion object {
        private const val PageSize = 25

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}