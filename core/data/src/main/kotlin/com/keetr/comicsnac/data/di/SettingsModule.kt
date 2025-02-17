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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsModule {
    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthDatastore(
        @ApplicationContext applicationContext: Context
    ) = PreferenceDataStoreFactory.create {
        applicationContext.preferencesDataStoreFile("auth")
    }

    @Provides
    @Singleton
    @Named("settings")
    fun provideSettingsDatastore(
        @ApplicationContext applicationContext: Context
    ) = PreferenceDataStoreFactory.create {
        applicationContext.preferencesDataStoreFile("settings")
    }

    @Provides
    fun provideColorSchemeKey() = intPreferencesKey("color_scheme")

    @Provides
    @Named("layout")
    fun provideLayoutPrefKey() = stringPreferencesKey("layout")

    @Provides
    @Named("auth")
    fun provideAuthKey() = stringPreferencesKey("auth")
}