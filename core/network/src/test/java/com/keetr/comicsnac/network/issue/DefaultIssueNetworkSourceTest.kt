package com.keetr.comicsnac.network.issue

import android.content.Context
import com.keetr.comicsnac.network.createClient
import com.keetr.comicsnac.network.issue.fake.AmazingSpidermanIssueResponse
import com.keetr.comicsnac.network.issue.fake.IssuesResponse
import com.keetr.comicsnac.network.issue.fake.RecentIssuesResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DefaultIssueNetworkSourceTest {

    private lateinit var client: HttpClient
    private lateinit var networkSource: IssueNetworkSource

    @JvmField
    @Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        val mockEngine = MockEngine { request ->
            respond(
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                content = when (request.url.encodedPath) {
                    "/api/issue/4000-369103/" -> AmazingSpidermanIssueResponse
                    "/api/issues" -> with(request.url.parameters) {
                        when {
                            contains("sort", "cover_date:desc") -> RecentIssuesResponse
                            else -> IssuesResponse
                        }
                    }

                    else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
                }
            )
        }

        val context = mockk<Context>()
        every { context.cacheDir } returns temporaryFolder.newFile()
        client = createClient(mockEngine, context)
        networkSource = DefaultIssueNetworkSource(client)
    }

    @After
    fun tearDown() {
        client.close()
    }

    @Test
    fun `verify issue number`() = runTest {
        val response =
            networkSource.getIssueDetails("https://comicvine.gamespot.com/api/issue/4000-369103/")
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