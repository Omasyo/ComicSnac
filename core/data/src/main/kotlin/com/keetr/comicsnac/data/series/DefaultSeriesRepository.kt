package com.keetr.comicsnac.data.series

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.movie.MovieDetails
import com.keetr.comicsnac.model.series.Series
import com.keetr.comicsnac.model.series.SeriesDetails
import com.keetr.comicsnac.network.movie.MovieNetworkSource
import com.keetr.comicsnac.network.movie.models.MovieListApiModel
import com.keetr.comicsnac.network.movie.models.MovieListResponse
import com.keetr.comicsnac.network.series.SeriesNetworkSource
import com.keetr.comicsnac.network.series.models.SeriesListApiModel
import com.keetr.comicsnac.network.series.models.SeriesListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultSeriesRepository @Inject constructor(
    private val networkSource: SeriesNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SeriesRepository {
    override fun getMovieDetails(id: String): Flow<RepositoryResponse<SeriesDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getSeriesDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toSeriesDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getRecentSeries(): Flow<RepositoryResponse<List<Series>>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getAllSeries(apiKey, 25, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toSeries()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllSeries(): Flow<PagingData<Series>> =
        getPagingData { apiKey, page ->
            networkSource.getAllSeries(
                apiKey,
                PageSize,
                PageSize * page
            )
        }

    override fun getSeriesWithId(seriesId: List<Int>): Flow<PagingData<Series>> =
        getPagingData { apiKey, page ->
            networkSource.getSeriesWithId(
                apiKey,
                PageSize,
                PageSize * page,
                seriesId = seriesId
            )
        }

    private fun getPagingData(init: suspend (apiKey: String, page: Int) -> Result<SeriesListResponse>): Flow<PagingData<Series>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        init(apiKey, page).getOrThrow().results
                    },
                    mapper = List<SeriesListApiModel>::toSeries
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