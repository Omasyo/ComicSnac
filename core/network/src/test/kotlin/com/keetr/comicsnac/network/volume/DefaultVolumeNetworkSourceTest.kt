package com.keetr.comicsnac.network.volume

import com.keetr.comicsnac.network.NetworkSourceTest
import com.keetr.comicsnac.network.volume.fake.VolumeDetailsResponse
import com.keetr.comicsnac.network.volume.fake.VolumesResponse
import io.ktor.client.request.HttpRequestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class DefaultVolumeNetworkSourceTest : NetworkSourceTest<VolumeNetworkSource>() {

    override fun generateResponseBody(request: HttpRequestData): String =
        when (request.url.encodedPath) {
            "/api/volume/4050-153158" -> VolumeDetailsResponse
            "/api/volumes" -> VolumesResponse
            else -> throw NotImplementedError("Invalid Url Path: ${request.url.encodedPath}")
        }

    @Before
    override fun initNetworkSource() {
        networkSource = DefaultVolumeNetworkSource(client)
    }

    @Test
    fun getVolumeDetails() = runTest {
        val response = networkSource.getVolumeDetails(apiKey, "153158").getOrThrow()
        assertEquals(153158, response.results.id)
    }

    @Test
    fun getAllVolumes() = runTest {
        val response = networkSource.getAllVolumes(apiKey, 100, 0).getOrThrow()
        assertEquals("Single Series", response.results.first().name)
    }
}