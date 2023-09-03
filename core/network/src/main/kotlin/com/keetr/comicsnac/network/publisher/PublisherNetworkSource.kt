package com.keetr.comicsnac.network.publisher

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.publisher.models.PublisherCharactersResponse
import com.keetr.comicsnac.network.publisher.models.PublisherDetailsResponse
import com.keetr.comicsnac.network.publisher.models.PublisherListResponse
import com.keetr.comicsnac.network.publisher.models.PublisherVolumesResponse

interface PublisherNetworkSource : NetworkSource {
    suspend fun getPublisherDetails(apiKey: String, id: String): Result<PublisherDetailsResponse>

    suspend fun getPublisherCharacters(apiKey: String, id: String): Result<PublisherCharactersResponse>

    suspend fun getPublisherVolumes(apiKey: String, id: String): Result<PublisherVolumesResponse>

    suspend fun getPublishersWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        publisherId: List<Int>,
    ): Result<PublisherListResponse>

    suspend fun getAllPublishers(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<PublisherListResponse>
}