package com.keetr.comicsnac.network.power

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.power.models.PowerDetailsResponse
import com.keetr.comicsnac.network.power.models.PowerListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DefaultPowerNetworkSource @Inject constructor(
    private val client: HttpClient
) : PowerNetworkSource {
    override suspend fun getPowerDetails(apiKey: String, id: String): Result<PowerDetailsResponse> =
        makeRequest {
            client.get("power/4035-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllPowers(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<PowerListResponse> =
        makeRequest {
            client.get("powers") {
                parameter("api_key", apiKey)
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
            }
        }
}

private const val DetailsFieldList = "api_detail_url,characters,description,id,name,site_detail_url"

private const val ListFieldList =
    "api_detail_url,id,name"