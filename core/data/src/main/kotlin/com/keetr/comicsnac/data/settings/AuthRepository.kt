package com.keetr.comicsnac.data.settings

import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.team.TeamDetails
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun verifyApiKey(key: String) : RepositoryResponse<Unit>

    suspend fun updateApiKey(key: String)

    fun getApiKey(): Flow<String>
}