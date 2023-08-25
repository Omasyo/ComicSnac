package com.keetr.comicsnac.data.volume

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.model.volume.VolumeDetails
import com.keetr.comicsnac.network.volume.VolumeNetworkSource
import com.keetr.comicsnac.network.volume.models.VolumeListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultVolumeRepository @Inject constructor(
    private val networkSource: VolumeNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : VolumeRepository{
    override fun getVolumeDetails(id: String): Flow<RepositoryResponse<VolumeDetails>> = flow{
        emit(networkSource.getVolumeDetails(id)
            .fold(onSuccess = {RepositoryResponse.Success(it.results.toVolumeDetails())}) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getRecentVolumes(): Flow<RepositoryResponse<List<Volume>>> = flow {
        emit(networkSource.getAllVolumes(25, 0)
            .fold(onSuccess = {RepositoryResponse.Success(it.results.toVolumes()) }){
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getAllVolumes(): Flow<PagingData<Volume>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getAllVolumes(
                        PageSize,
                        PageSize * page
                    ).getOrThrow().results
                },
                mapper = List<VolumeListApiModel>::toVolumes
            )
        }.flow.flowOn(dispatcher)

    override fun getVolumesWithId(volumesId: List<Int>): Flow<PagingData<Volume>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getVolumesWithId(
                        PageSize,
                        PageSize * page,
                        volumesId
                    ).getOrThrow().results
                },
                mapper = List<VolumeListApiModel>::toVolumes
            )
        }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = DefaultCharacterRepository.PageSize, enablePlaceholders = false
        )
    }
}