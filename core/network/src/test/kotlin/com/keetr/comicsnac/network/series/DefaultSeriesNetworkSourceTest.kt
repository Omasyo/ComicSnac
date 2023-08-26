package com.keetr.comicsnac.network.series

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.series.fake.SeriesDetailsResponse
import com.keetr.comicsnac.network.series.fake.SeriesResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultSeriesNetworkSourceTest : NetworkSourceTest<SeriesNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/series/4075-1217" -> SeriesDetailsResponse
            "/api/series_list" -> SeriesResponse

            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }


    @Before
    override fun initNetworkSource() {
        networkSource = DefaultSeriesNetworkSource(client)
    }


    @Test
    fun getSeriesDetails() = runTest {
        val response = networkSource.getSeriesDetails("1217").getOrThrow().results
        assertEquals(1217, response.id)
    }

    @Test
    fun getAllSeries() = runTest {
        val response = networkSource.getAllSeries(100, 0).getOrThrow().results
        val series = response.find { it.id == 99 }
        assertEquals("Adventure Time", series?.name)
    }
}