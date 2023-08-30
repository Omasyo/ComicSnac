package com.keetr.comicsnac.data.`object`

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.`object`.ObjectDetails
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.model.volume.VolumeDetails
import com.keetr.comicsnac.network.`object`.ObjectNetworkSource
import com.keetr.comicsnac.network.search.models.ObjectListApiModel
import com.keetr.comicsnac.network.volume.VolumeNetworkSource
import com.keetr.comicsnac.network.search.models.VolumeListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultObjectRepository @Inject constructor(
    private val networkSource: ObjectNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ObjectRepository {
    override fun getObjectDetails(id: String): Flow<RepositoryResponse<ObjectDetails>> = flow {
        emit(networkSource.getObjectDetails(id)
            .fold(onSuccess = { RepositoryResponse.Success(it.results.toObjectDetails()) }) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getAllObjects(): Flow<PagingData<ObjectItem>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getAllObjects(
                        PageSize,
                        PageSize * page
                    ).getOrThrow().results
                },
                mapper = List<ObjectListApiModel>::toObjectItems
            )
        }.flow.flowOn(dispatcher)

    override fun getObjectsWithId(objectsId: List<Int>): Flow<PagingData<ObjectItem>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getObjectsWithId(
                        PageSize,
                        PageSize * page,
                        objectsId = objectsId
                    ).getOrThrow().results
                },
                mapper = List<ObjectListApiModel>::toObjectItems
            )
        }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}