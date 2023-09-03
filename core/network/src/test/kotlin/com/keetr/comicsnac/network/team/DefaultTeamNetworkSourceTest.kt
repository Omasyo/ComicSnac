package com.keetr.comicsnac.network.team

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.team.fake.NewMutantsResponse
import com.keetr.comicsnac.network.team.fake.TeamsResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultTeamNetworkSourceTest : NetworkSourceTest<TeamNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/team/4060-15971" -> NewMutantsResponse
            "/api/teams" -> TeamsResponse
            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }

    @Before
    override fun initNetworkSource() {
        networkSource = DefaultTeamNetworkSource(client)
    }

    @Test
    fun getTeamDetails() = runTest {
        val response = networkSource.getTeamDetails(apiKey, "15971")
        assertEquals("New Mutants", response.getOrNull()?.results?.name)
    }

    @Test
    fun getAllTeams() = runTest {
        val response = networkSource.getAllTeams(apiKey, 100, 0)
        assertEquals("Metagen", response.getOrThrow().results.firstOrNull()?.name)
    }
}