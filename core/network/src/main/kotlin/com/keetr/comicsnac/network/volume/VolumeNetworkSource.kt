package com.keetr.comicsnac.network.volume

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.volume.models.VolumeDetailsResponse
import com.keetr.comicsnac.network.volume.models.VolumeListResponse

interface VolumeNetworkSource : NetworkSource {
    suspend fun getVolumeDetails(apiKey: String, id: String): Result<VolumeDetailsResponse>

    suspend fun getAllVolumes(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<VolumeListResponse>

    suspend fun getVolumesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        volumeIds: List<Int>
    ): Result<VolumeListResponse>
}