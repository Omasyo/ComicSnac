package com.keetr.comicsnac.data.settings

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.network.common.RandomNetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

internal class DefaultAuthRepository @Inject constructor(

    @Named("auth") private val settingsDataStore: DataStore<Preferences>,
    @Named("auth") private val authKey: Preferences.Key<String>,
    private val randomNetworkSource: RandomNetworkSource
) : AuthRepository {
    override suspend fun verifyApiKey(key: String): RepositoryResponse<Unit> =
        randomNetworkSource.verifyApiKey(key)
            .fold(onSuccess = { RepositoryResponse.Success(Unit) }) {
                Log.w("DefaultAuthRepository", "verifyApiKey: $it")
                fromNetworkError(it)
            }


    override suspend fun updateApiKey(key: String) {
        settingsDataStore.edit { preferences ->
            preferences[authKey] = key
        }
    }

    override fun getApiKey(): Flow<String> {
        return settingsDataStore.data.map { preferences ->
            preferences[authKey] ?: ""
        }
    }
}