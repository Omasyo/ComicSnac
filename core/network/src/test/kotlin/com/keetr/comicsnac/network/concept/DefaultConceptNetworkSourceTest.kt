package com.keetr.comicsnac.network.concept

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.concept.fake.ConceptsResponse
import com.keetr.comicsnac.network.concept.fake.OdinForceDetailsResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultConceptNetworkSourceTest : NetworkSourceTest<ConceptNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData)  = when (request.url.encodedPath) {
        "/api/concept/4015-35070" -> com.keetr.comicsnac.network.concept.fake.OdinForceDetailsResponse
        "/api/concepts" -> com.keetr.comicsnac.network.concept.fake.ConceptsResponse
        else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
    }

    @Before
    override fun initNetworkSource() {
        networkSource = DefaultConceptNetworkSource(client)
    }

    @Test
    fun getConceptDetails() = runTest {
        val response =
            networkSource.getConceptDetails("35070")
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