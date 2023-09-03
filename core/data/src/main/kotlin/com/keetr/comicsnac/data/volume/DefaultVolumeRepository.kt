package com.keetr.comicsnac.data.volume

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.model.volume.VolumeDetails
import com.keetr.comicsnac.network.volume.VolumeNetworkSource
import com.keetr.comicsnac.network.search.models.VolumeListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultVolumeRepository @Inject constructor(
    private val networkSource: VolumeNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : VolumeRepository {
    override fun getVolumeDetails(id: String): Flow<RepositoryResponse<VolumeDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getVolumeDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toVolumeDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getRecentVolumes(): Flow<RepositoryResponse<List<Volume>>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getAllVolumes(apiKey, 25, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toVolumes()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllVolumes(): Flow<PagingData<Volume>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllVolumes(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    },
                    mapper = List<VolumeListApiModel>::toVolumes
                )
            }.flow
        }.flowOn(dispatcher)

    override fun getVolumesWithId(volumesId: List<Int>): Flow<PagingData<Volume>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        val offset = PageSize * page
                        val pageList = volumesId.subList(
                            volumesId.size.coerceAtMost(offset),
                            volumesId.size.coerceAtMost(offset + DefaultCharacterRepository.PageSize)
                        )
                        if (pageList.isNotEmpty()) {
                            networkSource.getVolumesWithId(
                                apiKey,
                                PageSize,
                                0,
                                emptyList()
                            ).getOrThrow().results
                        } else emptyList()
                    },
                    mapper = List<VolumeListApiModel>::toVolumes
                )
            }.flow
        }.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = DefaultCharacterRepository.PageSize, enablePlaceholders = false
        )
    }
}