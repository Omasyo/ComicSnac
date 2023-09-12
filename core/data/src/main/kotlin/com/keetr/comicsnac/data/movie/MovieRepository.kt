package com.keetr.comicsnac.data.movie

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.movie.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovieDetails(id: String): Flow<RepositoryResponse<MovieDetails>>

    fun getRecentMovies(): Flow<RepositoryResponse<List<Movie>>>

    fun getAllMovies(): Flow<PagingData<Movie>>

    fun getMoviesWithId(moviesId: List<Int>): Flow<PagingData<Movie>>
}