package com.keetr.comicsnac.data.settings

import com.keetr.comicsnac.model.LayoutType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun updateColorSchemeId(id: Int)

    fun getColorSchemeId(): Flow<Int>

    suspend fun updateLayoutPreference(layout: LayoutType)

    fun getLayoutPreference(): Flow<LayoutType>
}