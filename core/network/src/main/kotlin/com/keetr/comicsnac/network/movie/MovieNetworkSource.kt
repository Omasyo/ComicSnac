package com.keetr.comicsnac.network.movie

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.movie.models.MovieDetailsResponse
import com.keetr.comicsnac.network.movie.models.MovieListResponse

interface MovieNetworkSource : NetworkSource {
    suspend fun getMovieDetails(apiKey: String, id: String): Result<MovieDetailsResponse>

    suspend fun getRecentMovies(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<MovieListResponse>

    suspend fun getAllMovies(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort = Sort.Descending
    ): Result<MovieListResponse>

    suspend fun getMoviesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort = Sort.Descending,
        moviesId: List<Int>
    ): Result<MovieListResponse>
}