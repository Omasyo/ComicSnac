package com.keetr.comicsnac.data.storyarc

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.storyarc.StoryArcDetails
import com.keetr.comicsnac.network.search.models.StoryArcListApiModel
import com.keetr.comicsnac.network.storyarc.StoryArcNetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultStoryArcRepository @Inject constructor(
    private val networkSource: StoryArcNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : StoryArcRepository {
    override fun getStoryArcDetails(id: String): Flow<RepositoryResponse<StoryArcDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getStoryArcDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toStoryArcDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getStoryArcsWithId(storyArcsId: List<Int>): Flow<PagingData<StoryArc>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getStoryArcsWithId(
                            apiKey,
                            PageSize,
                            PageSize * page,
                            storyArcsId
                        ).getOrThrow().results
                    }, mapper = List<StoryArcListApiModel>::toStoryArcs
                )
            }.flow
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllStoryArcs(): Flow<PagingData<StoryArc>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllStoryArcs(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    }, mapper = List<StoryArcListApiModel>::toStoryArcs
                )
            }.flow
        }.flowOn(dispatcher)

    companion object {
        private const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}