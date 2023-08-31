package com.keetr.comicsnac.data.publisher

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.publisher.Publisher
import com.keetr.comicsnac.model.publisher.PublisherDetails
import com.keetr.comicsnac.network.publisher.PublisherNetworkSource
import com.keetr.comicsnac.network.search.models.PublisherListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultPublisherRepository @Inject constructor(
    private val networkSource: PublisherNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : PublisherRepository {
    override fun getPublisherDetails(id: String): Flow<RepositoryResponse<PublisherDetails>> =
        flow {
            emit(networkSource.getPublisherDetails(id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toPublisherDetails()) }) {
                    fromNetworkError(it)
                })
        }.flowOn(dispatcher)

    override fun getPublisherCharactersId(id: String): Flow<RepositoryResponse<List<Int>>> = flow {
        emit(networkSource.getPublisherCharacters(id)
            .fold(onSuccess = { RepositoryResponse.Success(it.results.characters.map { character -> character.id }) }) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getPublisherVolumesId(id: String): Flow<RepositoryResponse<List<Int>>> = flow {
        emit(networkSource.getPublisherVolumes(id)
            .fold(onSuccess = { RepositoryResponse.Success(it.results.volumes.map { volume -> volume.id }) }) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getPopularPublishers(): Flow<RepositoryResponse<List<Publisher>>> = flow {
        emit(networkSource.getPublishersWithId(
            100, 0, listOf(31, 10, 513, 364, 1190, 485, 1859, 6583, 1868, 101)
        ).fold(onSuccess = { RepositoryResponse.Success(it.results.toPublishers()) }) {
            fromNetworkError(it)
        })
    }.flowOn(dispatcher)

    override fun getAllPublishers(): Flow<PagingData<Publisher>> = Pager(
        config = pagingConfig
    ) {
        CustomPagingSource(
            provider = { page ->
                networkSource.getAllPublishers(
                    PageSize, PageSize * page
                ).getOrThrow().results
            }, mapper = List<PublisherListApiModel>::toPublishers
        )
    }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}