package com.keetr.comicsnac.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.team.toTeamDetails
import com.keetr.comicsnac.model.team.TeamDetails
import com.keetr.comicsnac.network.common.RandomNetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject


//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


internal class DefaultAuthRepository @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>,
    private val authKey: Preferences.Key<String>,
    private val randomNetworkSource: RandomNetworkSource
) : AuthRepository {
    override suspend fun verifyApiKey(key: String): RepositoryResponse<Unit> =
        randomNetworkSource.verifyApiKey(key)
            .fold(onSuccess = { RepositoryResponse.Success(Unit) }) {
                fromNetworkError(it)
            }


    override suspend fun updateApiKey(key: String) {
        TODO("Not yet implemented")
    }

    override fun getApiKey(): Flow<String> {
        return flowOf("81ae8b1a284eaf1102e976c3d14a06ee72a3c5cc")
    }
}