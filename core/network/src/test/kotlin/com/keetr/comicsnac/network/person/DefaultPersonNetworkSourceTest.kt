package com.keetr.comicsnac.network.person

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.issue.DefaultIssueNetworkSource
import com.keetr.comicsnac.network.issue.fake.AmazingSpidermanIssueResponse
import com.keetr.comicsnac.network.issue.fake.RecentIssuesResponse
import com.keetr.comicsnac.network.person.fake.PeopleResponse
import com.keetr.comicsnac.network.person.fake.PersonResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import java.time.LocalDateTime

class DefaultPersonNetworkSourceTest : NetworkSourceTest<PersonNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/person/4040-114458" -> PersonResponse
            "/api/people" -> PeopleResponse
            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }


    @Before
    override fun initNetworkSource() {
        networkSource = DefaultPersonNetworkSource(client)
    }

    @Test
    fun getPersonDetails() = runTest {
        val response = networkSource.getPersonDetails("114458").getOrThrow()
        assertEquals(LocalDateTime.of(1933, 12, 10, 0, 0), response.results.birth)
    }

    @Test
    fun getAllPeople() = runTest {
        val response = networkSource.getAllPeople(100, 0).getOrThrow()
        assertEquals("Sabamizore", response.results.last().name)
    }
}