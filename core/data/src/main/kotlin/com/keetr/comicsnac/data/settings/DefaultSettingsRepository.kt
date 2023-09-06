package com.keetr.comicsnac.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.keetr.comicsnac.model.LayoutType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

internal class DefaultSettingsRepository @Inject constructor(
    @Named("settings") private val settingsDataStore: DataStore<Preferences>,
    private val colorSchemeKey: Preferences.Key<Int>,
    @Named("layout") private val layoutPrefKey: Preferences.Key<String>
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

    override suspend fun updateLayoutPreference(layout: LayoutType) {
        settingsDataStore.edit { preferences ->
            preferences[layoutPrefKey] = layout.name
        }
    }

    override fun getLayoutPreference(): Flow<LayoutType> {
        return settingsDataStore.data.map { preferences ->
            LayoutType.valueOf(preferences[layoutPrefKey] ?: LayoutType.Grid.name)
        }
    }
}