package com.keetr.comicsnac.data.concept

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.concept.ConceptDetails
import kotlinx.coroutines.flow.Flow

interface ConceptRepository {
    fun getConceptDetails(id: String): Flow<RepositoryResponse<ConceptDetails>>

    fun getAllConcepts(): Flow<PagingData<Concept>>
}