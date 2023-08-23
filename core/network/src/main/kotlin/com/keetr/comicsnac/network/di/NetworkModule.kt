package com.keetr.comicsnac.network.di

import android.content.Context
import com.keetr.comicsnac.network.character.CharacterNetworkSource
import com.keetr.comicsnac.network.character.DefaultCharacterNetworkSource
import com.keetr.comicsnac.network.concept.ConceptNetworkSource
import com.keetr.comicsnac.network.concept.DefaultConceptNetworkSource
import com.keetr.comicsnac.network.createClient
import com.keetr.comicsnac.network.issue.DefaultIssueNetworkSource
import com.keetr.comicsnac.network.issue.IssueNetworkSource
import com.keetr.comicsnac.network.team.DefaultTeamNetworkSource
import com.keetr.comicsnac.network.team.TeamNetworkSource
import com.keetr.comicsnac.network.volume.DefaultVolumeNetworkSource
import com.keetr.comicsnac.network.volume.VolumeNetworkSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
private object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClientEngine() : HttpClientEngine = CIO.create()

    @Provides
    @Singleton
    fun provideHttpClient(
        engine: HttpClientEngine,
        @ApplicationContext applicationContext: Context
    ) =
        createClient(engine, applicationContext)

    @Provides
    @Singleton
    fun provideCharacterNetworkSource(client: HttpClient): CharacterNetworkSource =
        DefaultCharacterNetworkSource(client)


    @Provides
    @Singleton
    fun provideConceptNetworkSource(client: HttpClient): ConceptNetworkSource =
        DefaultConceptNetworkSource(client)


    @Provides
    @Singleton
    fun provideIssueNetworkSource(client: HttpClient): IssueNetworkSource =
        DefaultIssueNetworkSource(client)

    @Provides
    @Singleton
    fun provideTeamNetworkSource(client: HttpClient): TeamNetworkSource =
        DefaultTeamNetworkSource(client)

    @Provides
    @Singleton
    fun provideVolumeNetworkSource(client: HttpClient): VolumeNetworkSource =
        DefaultVolumeNetworkSource(client)
}