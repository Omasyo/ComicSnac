package com.keetr.comicsnac.network.character

import android.content.Context
import com.keetr.comicsnac.network.character.fake.BarquinDetailsResponse
import com.keetr.comicsnac.network.character.fake.BatmanDetailsResponse
import com.keetr.comicsnac.network.character.fake.CharactersResponse
import com.keetr.comicsnac.network.character.fake.FilteredCharactersResponse
import com.keetr.comicsnac.network.character.fake.MaleCharactersResponse
import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.createClient
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


class DefaultCharacterNetworkSourceTest {

    private lateinit var client: HttpClient
    private lateinit var networkSource: CharacterNetworkSource

    @JvmField
    @Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        val mockEngine = MockEngine { request ->
            respond(
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                content = when (request.url.encodedPath) {
                    "/api/character/4005-1699/" -> BatmanDetailsResponse
                    "/api/character/4005-184971/" -> BarquinDetailsResponse
                    "/api/characters" -> with(request.url.encodedQuery) {
                        when {
                            contains("gender") -> MaleCharactersResponse
                            contains("filter") -> FilteredCharactersResponse
                            else -> CharactersResponse
                        }
                    }
                    else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
                }
            )
        }

        val context = mockk<Context>()
        every{ context.cacheDir } returns temporaryFolder.newFile()
        client = createClient(mockEngine, context)
        networkSource = DefaultCharacterNetworkSource(client)
    }

    @After
    fun tearDown() {
        client.close()
    }

    @Test
    fun getBarquinCharacterDetails() = runTest {
        val response =
            networkSource.getCharacterDetails("https://comicvine.gamespot.com/api/character/4005-184971/")
        val issue = IssueApiModel(
            apiDetailUrl = "https://comicvine.gamespot.com/api/first_appeared_in_issue/4000-1006449/",
            id = 1006449,
            issueNumber = "1",
            name = "The Max Rebo Band"
        )
        assertEquals(issue, response.results.firstAppearedInIssue)
    }

    @Test
    fun getBatmanCharacterDetails() = runTest {
        val response =
            networkSource.getCharacterDetails("https://comicvine.gamespot.com/api/character/4005-1699/")
        assertEquals("Batman", response.results.name)
    }


    @Test
    fun getCharacters() = runTest {
        val response = networkSource.getAllCharacters(100, 0, GenderApiModel.All)
        assert(response.results.any { it.name == "Sal Martello" })
    }

    @Test
    fun getMaleCharacters() = runTest {
        val response = networkSource.getAllCharacters(100, 0, GenderApiModel.Male)
        assert(response.results.all { it.gender == GenderApiModel.Male })
    }

    @Test
    fun getCharactersWithId() = runTest {
        val ids = setOf(1443, 48499, 45927, 44135)
        val response = networkSource.getCharactersWithId(100, 0, ids.toList())
        assert(response.results.all { ids.contains(it.id) })
    }
}