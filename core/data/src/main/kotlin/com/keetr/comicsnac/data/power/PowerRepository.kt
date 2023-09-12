package com.keetr.comicsnac.data.power

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.model.power.PowerDetails
import kotlinx.coroutines.flow.Flow

interface PowerRepository {
    fun getPowerDetails(id: String): Flow<RepositoryResponse<PowerDetails>>

    fun getAllPowers(): Flow<PagingData<PowerBasic>>
}