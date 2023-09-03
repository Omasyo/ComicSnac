package com.keetr.comicsnac.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSettingsRepository @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>,
    private val colorSchemeKey: Preferences.Key<Int>,
    private val authKey: Preferences.Key<String>
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