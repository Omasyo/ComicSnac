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

    override suspend fun getPublisherDetails(id: Int): Result<PublisherDetailsResponse> =
        makeRequest {
            client.get("publisher/4010-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getPublisherCharacters(id: Int): Result<PublisherCharactersResponse> =
        makeRequest {
            client.get("publisher/4010-$id") {
                parameter("field_list", "characters")
            }
        }

    override suspend fun getPublisherVolumes(id: Int): Result<PublisherVolumesResponse> =
        makeRequest {
            client.get("publisher/4010-$id") {
                parameter("field_list", "volumes")
            }
        }

    override suspend fun getAllPublishers(
        pageSize: Int,
        offset: Int
    ): Result<PublisherListResponse> = makeRequest {
        client.get("issues") {
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