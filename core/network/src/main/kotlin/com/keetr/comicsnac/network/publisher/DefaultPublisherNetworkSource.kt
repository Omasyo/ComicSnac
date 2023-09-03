package com.keetr.comicsnac.network.publisher

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.publisher.models.PublisherCharactersResponse
import com.keetr.comicsnac.network.publisher.models.PublisherDetailsResponse
import com.keetr.comicsnac.network.publisher.models.PublisherListResponse
import com.keetr.comicsnac.network.publisher.models.PublisherVolumesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultPublisherNetworkSource @Inject constructor(
    private val client: HttpClient
) : PublisherNetworkSource {

    override suspend fun getPublisherDetails(
        apiKey: String,
        id: String
    ): Result<PublisherDetailsResponse> =
        makeRequest {
            client.get("publisher/4010-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getPublisherCharacters(
        apiKey: String,
        id: String
    ): Result<PublisherCharactersResponse> =
        makeRequest {
            client.get("publisher/4010-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", "characters")
            }
        }

    override suspend fun getPublisherVolumes(
        apiKey: String,
        id: String
    ): Result<PublisherVolumesResponse> =
        makeRequest {
            client.get("publisher/4010-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", "volumes")
            }
        }

    override suspend fun getPublishersWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        publisherId: List<Int>
    ): Result<PublisherListResponse> = makeRequest {
        client.get("publishers") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            parameter("sort", "date_last_updated:${Sort.Descending.format}")
            parameter("filter", "id:${publisherId.joinToString("|")}")
        }
    }

    override suspend fun getAllPublishers(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<PublisherListResponse> = makeRequest {
        client.get("publishers") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            parameter("sort", "date_last_updated:${Sort.Descending.format}")
        }
    }
}

private const val DetailsFieldList = "aliases,api_detail_url,deck,description,id,image," +
        "location_address,location_city,location_state,name,site_detail_url,teams"

private const val ListFieldList =
    "api_detail_url,deck,id,image,name"