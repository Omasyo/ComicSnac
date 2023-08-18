package com.keetr.comicsnac.network.issue

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.issue.fake.AmazingSpidermanIssueResponse
import com.keetr.comicsnac.network.issue.fake.IssuesResponse
import com.keetr.comicsnac.network.issue.fake.RecentIssuesResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultIssueNetworkSourceTest : NetworkSourceTest<IssueNetworkSource>() {
    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/issue/4000-369103" -> AmazingSpidermanIssueResponse
            "/api/issues" -> with(request.url.parameters) {
                when {
                    contains("sort", "cover_date:desc") -> RecentIssuesResponse
                    else -> IssuesResponse
                }
            }

            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }


    @Before
    override fun initNetworkSource() {
        networkSource = DefaultIssueNetworkSource(client)
    }


    @Test
    fun `verify issue number`() = runTest {
        val response =
            networkSource.getIssueDetails("4000-369103")
        assertEquals("698", response.getOrThrow().results.issueNumber)
    }

    @Test
    fun `verify characters sorted by cover date`() = runTest {
        val response = networkSource.getRecentIssues(100, 0)
        val issues = response.getOrThrow().results
        assertEquals(issues.sortedByDescending { it.coverDate }, issues)
    }

    @Test
    fun getAllIssues() = runTest {
        val response = networkSource.getAllIssues(100, 0)
        assertEquals(
            "https://comicvine.gamespot.com/api/issue/4000-6/",
            response.getOrThrow().results.first().apiDetailUrl
        )
    }
}