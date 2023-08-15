package com.keetr.comicsnac.network

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

abstract class NetworkSourceTest<T: NetworkSource> {
    protected lateinit var client: HttpClient
    protected lateinit var networkSource: T

    abstract fun generateResponseBody(request: HttpRequestData): String

    @JvmField
    @Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        val mockEngine = MockEngine { request ->
            respond(
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                content = generateResponseBody(request)
            )
        }

        val context = mockk<Context>()
        every { context.cacheDir } returns temporaryFolder.newFile()
        client = createClient(mockEngine, context)
    }

    @Before
    abstract fun initNetworkSource()

    @After
    fun tearDown() {
        client.close()
    }
}