package com.keetr.comicsnac.network.movie

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.movie.models.MovieDetailsResponse
import com.keetr.comicsnac.network.movie.models.MovieListResponse

interface MovieNetworkSource : NetworkSource {
    suspend fun getMovieDetails(id: String): Result<MovieDetailsResponse>

    suspend fun getRecentMovies(pageSize: Int, offset: Int): Result<MovieListResponse>

    suspend fun getAllMovies(
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort = Sort.Descending
    ): Result<MovieListResponse>

    suspend fun getMoviesWithId(
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort = Sort.Descending,
        moviesId: List<Int>
    ): Result<MovieListResponse>
}