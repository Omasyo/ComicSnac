package com.keetr.comicsnac.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSettingsRepository @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>,
    private val colorSchemeKey: Preferences.Key<Int>
) : SettingsRepository {

    override suspend fun updateColorSchemeId(id: Int) {
        settingsDataStore.edit { preferences ->
            preferences[colorSchemeKey] = id
        }
    }

    override fun getColorSchemeId(): Flow<Int> {
        return settingsDataStore.data.map { preferences ->
            preferences[colorSchemeKey] ?: 0
        }
    }
}