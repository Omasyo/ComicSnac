package com.keetr.comicsnac.network.origin

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.origin.models.OriginDetailsResponse
import com.keetr.comicsnac.network.origin.models.OriginListResponse

interface OriginNetworkSource : NetworkSource {

    suspend fun getOriginDetails(apiKey: String, id: String): Result<OriginDetailsResponse>

    suspend fun getAllOrigins(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<OriginListResponse>
}