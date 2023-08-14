package com.keetr.comicsnac.network.concept

import android.content.Context
import com.keetr.comicsnac.network.concept.fake.ConceptsResponse
import com.keetr.comicsnac.network.concept.fake.OdinForceDetailsResponse
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

class DefaultConceptNetworkSourceTest {

    private lateinit var client: HttpClient
    private lateinit var networkSource: ConceptNetworkSource

    @JvmField
    @Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        val mockEngine = MockEngine { request ->
            respond(
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                content = when (request.url.encodedPath) {
                    "/api/concept/4015-35070/" -> OdinForceDetailsResponse
                    "/api/concepts" -> ConceptsResponse
                    else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
                }
            )
        }

        val context = mockk<Context>()
        every{ context.cacheDir } returns temporaryFolder.newFile()
        client = createClient(mockEngine, context)
        networkSource = DefaultConceptNetworkSource(client)
    }

    @After
    fun tearDown() {
        client.close()
    }

    @Test
    fun getConceptDetails() = runTest {
        val response =
            networkSource.getConceptDetails("https://comicvine.gamespot.com/api/concept/4015-35070/")
        assertEquals("The Odin Force", response.getOrNull()?.results?.name)
    }

    @Test
    fun getAllConcepts() = runTest{
        val response =
            networkSource.getAllConcepts(100, 0)
        val concepts = response.getOrThrow().results
        assert(concepts.any { it.name == "The Odin Force" })
        assertEquals(100, concepts.size)

    }
}