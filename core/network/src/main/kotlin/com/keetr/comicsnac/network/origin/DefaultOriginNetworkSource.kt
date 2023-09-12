package com.keetr.comicsnac.network.origin

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.origin.DetailsFieldList
import com.keetr.comicsnac.network.origin.ListFieldList
import com.keetr.comicsnac.network.origin.OriginNetworkSource
import com.keetr.comicsnac.network.origin.models.OriginDetailsResponse
import com.keetr.comicsnac.network.origin.models.OriginListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DefaultOriginNetworkSource @Inject constructor(
    private val client: HttpClient
) : OriginNetworkSource {
    override suspend fun getOriginDetails(apiKey: String, id: String): Result<OriginDetailsResponse> =
        makeRequest {
            client.get("origin/4030-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllOrigins(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<OriginListResponse> =
        makeRequest {
            client.get("origins") {
                parameter("api_key", apiKey)
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
            }
        }
}

private const val DetailsFieldList = "api_detail_url,character_set,id,name,site_detail_url"

private const val ListFieldList =
    "api_detail_url,id,name"