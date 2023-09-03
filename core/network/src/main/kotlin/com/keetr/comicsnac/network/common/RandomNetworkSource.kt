package com.keetr.comicsnac.network.common

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

interface RandomNetworkSource : NetworkSource {
    suspend fun verifyApiKey(apiKey: String): Result<ResponseApiModel<List<Nothing>>>
}

internal class DefaultRandomNetworkSource @Inject constructor(
    private val client: HttpClient
) : RandomNetworkSource {
    override suspend fun verifyApiKey(apiKey: String): Result<ResponseApiModel<List<Nothing>>> =
        makeRequest {
            client.get("chats") {
                parameter("api_key", apiKey)
            }
        }

}