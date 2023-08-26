package com.keetr.comicsnac.network.search

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.search.models.IssueListApiModel
import com.keetr.comicsnac.network.search.fake.SearchResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Before

import org.junit.Test

class DefaultSearchNetworkSourceTest : NetworkSourceTest<SearchNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/search" -> SearchResponse
            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }


    @Before
    override fun initNetworkSource() {
        networkSource = DefaultSearchNetworkSource(client)
    }


    @Test
    fun getSearchResults() = runTest {
        val response = networkSource.getSearchResults("french", 100, 0).getOrThrow().results
        assert(response.last() is IssueListApiModel)
    }
}