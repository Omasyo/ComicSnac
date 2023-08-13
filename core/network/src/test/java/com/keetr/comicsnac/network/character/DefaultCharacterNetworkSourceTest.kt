package com.keetr.comicsnac.network.character

import android.content.Context
import com.keetr.comicsnac.network.character.fake.BarquinDetailsResponse
import com.keetr.comicsnac.network.character.fake.BatmanDetailsResponse
import com.keetr.comicsnac.network.createClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.fullPath
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
    fun getCharacterDetails() = runTest {
        val details =
            networkSource.getCharacterDetails("https://comicvine.gamespot.com/api/character/4005-184971/")
        assertEquals(184971, details.results.id)
    }

    @Test
    fun getRecentCharacters() {
    }

    @Test
    fun getAllCharacters() {
    }

    @Test
    fun getCharactersWithId() {
    }
}