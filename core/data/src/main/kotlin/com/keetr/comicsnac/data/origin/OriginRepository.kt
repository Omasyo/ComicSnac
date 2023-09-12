package com.keetr.comicsnac.data.origin

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.origin.OriginBasic
import com.keetr.comicsnac.model.origin.OriginDetails
import kotlinx.coroutines.flow.Flow

interface OriginRepository {
    fun getOriginDetails(id: String): Flow<RepositoryResponse<OriginDetails>>

    fun getAllOrigins(): Flow<PagingData<OriginBasic>>
}