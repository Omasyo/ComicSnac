package com.keetr.comicsnac.data.location

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.location.LocationDetails
import com.keetr.comicsnac.network.location.LocationNetworkSource
import com.keetr.comicsnac.network.search.models.LocationListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultLocationRepository @Inject constructor(
    private val networkSource: LocationNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : LocationRepository {
    override fun getLocationDetails(id: String): Flow<RepositoryResponse<LocationDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getLocationDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toLocationDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllLocations(): Flow<PagingData<Location>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllLocations(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    },
                    mapper = List<LocationListApiModel>::toLocations
                )
            }.flow
        }.flowOn(dispatcher)

    override fun getLocationsWithId(locationsId: List<Int>): Flow<PagingData<Location>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getLocationsWithId(
                            apiKey,
                            PageSize,
                            PageSize * page,
                            locationsId = locationsId
                        ).getOrThrow().results
                    },
                    mapper = List<LocationListApiModel>::toLocations
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