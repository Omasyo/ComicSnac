package com.keetr.comicsnac.network.power

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.power.models.PowerDetailsResponse
import com.keetr.comicsnac.network.power.models.PowerListResponse

interface PowerNetworkSource : NetworkSource {

    suspend fun getPowerDetails(id: String): Result<PowerDetailsResponse>

    suspend fun getAllPowers(
        pageSize: Int,
        offset: Int
    ): Result<PowerListResponse>
}