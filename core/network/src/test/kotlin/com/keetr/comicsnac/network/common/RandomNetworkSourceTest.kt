package com.keetr.comicsnac.network.common

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.common.fake.EmptyResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RandomNetworkSourceTest : NetworkSourceTest<RandomNetworkSource>() {


    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/chats" -> EmptyResponse

            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }

    @Before
    override fun initNetworkSource() {
        networkSource = DefaultRandomNetworkSource(client)
    }


    @Test
    fun verifyApiKey() = runTest {
        val response =
            networkSource.verifyApiKey(apiKey).getOrThrow()
        assertEquals(1, response.statusCode)
    }
}