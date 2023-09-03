package com.keetr.comicsnac.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun updateColorScheme(id: Int)

    fun getColorScheme(): Flow<Int>
}