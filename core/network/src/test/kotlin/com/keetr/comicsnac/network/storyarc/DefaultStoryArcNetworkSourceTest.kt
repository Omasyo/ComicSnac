package com.keetr.comicsnac.network.storyarc

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.storyarc.fake.StoryArcDetailsResponse
import com.keetr.comicsnac.network.storyarc.fake.StoryArcsResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultStoryArcNetworkSourceTest : NetworkSourceTest<StoryArcNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/story_arc/4045-61122" -> StoryArcDetailsResponse
            "/api/story_arcs" -> StoryArcsResponse
            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }

    @Before
    override fun initNetworkSource() {
        networkSource = DefaultStoryArcNetworkSource(client)
    }


    @Test
    fun getStoryArcDetails() = runTest {
        val response = networkSource.getStoryArcDetails(apiKey, "61122").getOrThrow()
        assertEquals("The Catalyst War", response.results.name)
    }

    @Test
    fun getAllStoryArcs() = runTest {
        val response = networkSource.getAllStoryArcs(apiKey, 100, 0).getOrThrow()
        assertEquals("Red Son Rising", response.results.last().name)
    }
}