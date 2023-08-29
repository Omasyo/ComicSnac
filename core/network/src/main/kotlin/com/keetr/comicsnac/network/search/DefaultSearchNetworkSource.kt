package com.keetr.comicsnac.network.search

import android.util.Log
import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.search.models.SearchApiModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultSearchNetworkSource @Inject constructor(
    private val client: HttpClient
) : SearchNetworkSource {
    override suspend fun getSearchResults(
        query: String,
        filter: String,
        pageSize: Int,
        offset: Int
    ): Result<ResponseApiModel<List<SearchApiModel>>> = makeRequest {
        Log.d("SearchSource", "getSearchResults: $offset")
        client.get("search") {
            parameter("resources", filter)
            parameter("query", query)
            parameter("limit", pageSize)
            parameter("offset", offset)
        }
    }
}
