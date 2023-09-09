package com.keetr.comicsnac.network.movie

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.movie.models.MovieDetailsResponse
import com.keetr.comicsnac.network.movie.models.MovieListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultMovieNetworkSource @Inject constructor(
    private val client: HttpClient
) : MovieNetworkSource {
    override suspend fun getMovieDetails(apiKey: String, id: String): Result<MovieDetailsResponse> =
        makeRequest {
            client.get("movie/4025-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getRecentMovies(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<MovieListResponse> =
        getMovies(apiKey, pageSize, offset, Sort.Descending)

    override suspend fun getAllMovies(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort
    ): Result<MovieListResponse> =
        getMovies(apiKey, pageSize, offset, sortReleaseDate)

    override suspend fun getMoviesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort,
        moviesId: List<Int>
    ): Result<MovieListResponse> = getMovies(apiKey, pageSize, offset, sortReleaseDate, moviesId)

    private suspend fun getMovies(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortReleaseDate: Sort = Sort.None,
        moviesId: List<Int> = emptyList()
    ): Result<MovieListResponse> = makeRequest {
        client.get("movies") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (sortReleaseDate != Sort.None) parameter(
                "sort", "release_date:${sortReleaseDate.format}"
            )
            if (moviesId.isNotEmpty()) parameter(
                "filter", "id:${moviesId.joinToString("|")}"
            )
        }
    }
}

private const val DetailsFieldList = "api_detail_url,box_office_revenue,budget,characters,deck," +
        "description,id,image,locations,name,objects,producers,rating,release_date,runtime," +
        "site_detail_url,studios,teams,total_revenue,writers"

private const val ListFieldList =
    "api_detail_url,deck,id,image,name"