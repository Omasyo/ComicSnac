package com.keetr.comicsnac.network.movie

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.movie.fake.MovieDetailsResponse
import com.keetr.comicsnac.network.movie.fake.MoviesResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultMovieNetworkSourceTest : NetworkSourceTest<MovieNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/movie/4025-2232" -> MovieDetailsResponse
            "/api/movies" -> MoviesResponse

            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }


    @Before
    override fun initNetworkSource() {
        networkSource = DefaultMovieNetworkSource(client)
    }

    @Test
    fun getMovieDetails() = runTest {
        val response = networkSource.getMovieDetails(apiKey, "2232").getOrThrow().results
        assertEquals("72", response.runtime)
    }

    @Test
    fun getRecentMovies() = runTest {
        val response = networkSource.getAllMovies(apiKey, 100, 1).getOrThrow().results
        assertEquals("Quantum of Solace", response.last().name)
    }
}