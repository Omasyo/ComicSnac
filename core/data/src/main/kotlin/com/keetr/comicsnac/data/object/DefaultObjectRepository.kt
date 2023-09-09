package com.keetr.comicsnac.data.`object`

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.`object`.ObjectDetails
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.network.`object`.ObjectNetworkSource
import com.keetr.comicsnac.network.search.models.ObjectListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultObjectRepository @Inject constructor(
    private val networkSource: ObjectNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ObjectRepository {
    override fun getObjectDetails(id: String): Flow<RepositoryResponse<ObjectDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getObjectDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toObjectDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllObjects(): Flow<PagingData<ObjectItem>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllObjects(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    },
                    mapper = List<ObjectListApiModel>::toObjectItems
                )
            }.flow
        }.flowOn(dispatcher)

    override fun getObjectsWithId(objectsId: List<Int>): Flow<PagingData<ObjectItem>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getObjectsWithId(
                            apiKey,
                            PageSize,
                            PageSize * page,
                            objectsId = objectsId
                        ).getOrThrow().results
                    },
                    mapper = List<ObjectListApiModel>::toObjectItems
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