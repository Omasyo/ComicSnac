package com.keetr.comicsnac.network.concept

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.concept.models.ConceptDetailsResponse
import com.keetr.comicsnac.network.concept.models.ConceptListResponse

interface ConceptNetworkSource: NetworkSource {
    suspend fun getConceptDetails(apiUrl: String): Result<ConceptDetailsResponse>

    suspend fun getAllConcepts(
        pageSize: Int,
        offset: Int
    ): Result<ConceptListResponse>
}