package com.keetr.comicsnac.network.search

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.SearchApiModel

interface SearchNetworkSource : NetworkSource {
    suspend fun getSearchResults(
        apiKey: String,
        query: String,
        filter: String,
        pageSize: Int,
        offset: Int
    ): Result<ResponseApiModel<List<SearchApiModel>>>
}