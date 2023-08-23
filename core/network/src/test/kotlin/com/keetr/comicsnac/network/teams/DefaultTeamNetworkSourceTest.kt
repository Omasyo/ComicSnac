package com.keetr.comicsnac.network.teams

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.teams.fake.NewMutantsResponse
import com.keetr.comicsnac.network.teams.fake.TeamsResponse
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
        val response = networkSource.getTeamDetails("15971")
        assertEquals("New Mutants", response.getOrNull()?.results?.name)
    }

    @Test
    fun getAllTeams() = runTest {
        val response = networkSource.getAllTeams(100, 0)
        assertEquals("The Hand", response.getOrNull()?.results?.firstOrNull()?.name)
    }
}