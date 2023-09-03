package com.keetr.comicsnac.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun updateColorSchemeId(id: Int)

    fun getColorSchemeId(): Flow<Int>

    suspend fun updateApiKey(key: String)

    fun getApiKey(): Flow<String>
}