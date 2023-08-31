package com.keetr.comicsnac.network.publisher

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.publisher.models.PublisherCharactersResponse
import com.keetr.comicsnac.network.publisher.models.PublisherDetailsResponse
import com.keetr.comicsnac.network.publisher.models.PublisherListResponse
import com.keetr.comicsnac.network.publisher.models.PublisherVolumesResponse

interface PublisherNetworkSource : NetworkSource {
    suspend fun getPublisherDetails(id: String): Result<PublisherDetailsResponse>

    suspend fun getPublisherCharacters(id: String): Result<PublisherCharactersResponse>

    suspend fun getPublisherVolumes(id: String): Result<PublisherVolumesResponse>

    suspend fun getAllPublishers(
        pageSize: Int,
        offset: Int
    ): Result<PublisherListResponse>
}