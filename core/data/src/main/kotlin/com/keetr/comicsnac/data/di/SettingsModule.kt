package com.keetr.comicsnac.data.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object SettingsModule {
    @Provides
    @Singleton
    fun provideSettingsDatastore(
        @ApplicationContext applicationContext: Context
    ) = PreferenceDataStoreFactory.create {
        applicationContext.preferencesDataStoreFile("settings")
    }

    @Provides
    fun provideColorSchemeKey() = intPreferencesKey("color_scheme")

    @Provides
    fun provideAuthKey() = stringPreferencesKey("auth")
}