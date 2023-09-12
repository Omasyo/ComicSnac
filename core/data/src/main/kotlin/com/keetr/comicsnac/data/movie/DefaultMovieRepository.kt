package com.keetr.comicsnac.data.movie

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
import com.keetr.comicsnac.network.movie.MovieNetworkSource
import com.keetr.comicsnac.network.movie.models.MovieListApiModel
import com.keetr.comicsnac.network.movie.models.MovieListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultMovieRepository @Inject constructor(
    private val networkSource: MovieNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {
    override fun getMovieDetails(id: String): Flow<RepositoryResponse<MovieDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getMovieDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toMovieDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getRecentMovies(): Flow<RepositoryResponse<List<Movie>>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getRecentMovies(apiKey, 25, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toMovies()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllMovies(): Flow<PagingData<Movie>> =
        getPagingData { apiKey, page ->
            networkSource.getAllMovies(
                apiKey,
                PageSize,
                PageSize * page
            )
        }

    override fun getMoviesWithId(moviesId: List<Int>): Flow<PagingData<Movie>> =
        getPagingData { apiKey, page ->
            networkSource.getMoviesWithId(
                apiKey,
                PageSize,
                PageSize * page,
                moviesId = moviesId
            )
        }

    private fun getPagingData(init: suspend (apiKey: String, page: Int) -> Result<MovieListResponse>): Flow<PagingData<Movie>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        init(apiKey, page).getOrThrow().results
                    },
                    mapper = List<MovieListApiModel>::toMovies
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