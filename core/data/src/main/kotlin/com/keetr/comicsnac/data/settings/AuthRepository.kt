package com.keetr.comicsnac.data.settings

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun updateApiKey(key: String)

    fun getApiKey(): Flow<String>
}