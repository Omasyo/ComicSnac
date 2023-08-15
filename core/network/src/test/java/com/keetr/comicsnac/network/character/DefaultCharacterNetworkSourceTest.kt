package com.keetr.comicsnac.network.character

import android.content.Context
import com.keetr.comicsnac.network.character.fake.BarquinDetailsResponse
import com.keetr.comicsnac.network.character.fake.BatmanDetailsResponse
import com.keetr.comicsnac.network.character.fake.CharactersResponse
import com.keetr.comicsnac.network.character.fake.FilteredCharactersResponse
import com.keetr.comicsnac.network.character.fake.MaleCharactersResponse
import com.keetr.comicsnac.network.character.fake.RecentCharactersResponse
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
                    "/api/characters" -> with(request.url.parameters) {
                        when {
                            contains("gender", "1") -> MaleCharactersResponse
                            contains("filter") -> FilteredCharactersResponse
                            contains("sort", "date_last_updated:desc") -> RecentCharactersResponse
                            else -> CharactersResponse
                        }
                    }

                    else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
                }
            )
        }

        val context = mockk<Context>()
        every { context.cacheDir } returns temporaryFolder.newFile()
        client = createClient(mockEngine, context)
        networkSource = DefaultCharacterNetworkSource(client)
    }

    @After
    fun tearDown() {
        client.close()
    }

    @Test
    fun `verify character first issue`() = runTest {
        val response =
            networkSource.getCharacterDetails("https://comicvine.gamespot.com/api/character/4005-184971/")
        val issue = IssueApiModel(
            apiDetailUrl = "https://comicvine.gamespot.com/api/first_appeared_in_issue/4000-1006449/",
            id = 1006449,
            issueNumber = "1",
            name = "The Max Rebo Band"
        )
        assertEquals(issue, response.getOrNull()?.results?.firstAppearedInIssue)
    }

    @Test
    fun `verify character name`() = runTest {
        val response =
            networkSource.getCharacterDetails("https://comicvine.gamespot.com/api/character/4005-1699/")
        assertEquals("Batman", response.getOrNull()?.results?.name)
    }


    @Test
    fun `check result contains character name`() = runTest {
        val response = networkSource.getAllCharacters(100, 0, GenderApiModel.All)
        val characters = response.getOrThrow().results
        assert(characters.any { it.name == "Sal Martello" })
        assertEquals(100, characters.size)
    }

    @Test
    fun `verify filter characters by gender`() = runTest {
        val response = networkSource.getAllCharacters(100, 0, GenderApiModel.Male)
        assert(response.getOrThrow().results.all { it.gender == GenderApiModel.Male })
    }

    @Test
    fun `verify filter characters by id`() = runTest {
        val ids = setOf(1443, 48499, 45927, 44135)
        val response = networkSource.getCharactersWithId(100, 0, ids.toList())
        assert(response.getOrThrow().results.all { ids.contains(it.id) })
    }

    @Test
    fun `verify sort character by date last updated`() = runTest {
        val response = networkSource.getRecentCharacters(100, 0)
        val characters = response.getOrThrow().results
        assertEquals(characters.sortedByDescending { it.dateLastUpdate }, characters)
    }
}