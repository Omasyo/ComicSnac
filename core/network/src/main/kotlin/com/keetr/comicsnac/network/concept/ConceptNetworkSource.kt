package com.keetr.comicsnac.network.concept

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.concept.models.ConceptDetailsResponse
import com.keetr.comicsnac.network.concept.models.ConceptListResponse

interface ConceptNetworkSource : NetworkSource {
    suspend fun getConceptDetails(apiKey: String, id: String): Result<ConceptDetailsResponse>

    suspend fun getAllConcepts(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<ConceptListResponse>
}