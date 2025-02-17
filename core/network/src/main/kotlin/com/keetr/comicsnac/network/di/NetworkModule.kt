package com.keetr.comicsnac.network.di

import android.content.Context
import com.keetr.comicsnac.network.character.CharacterNetworkSource
import com.keetr.comicsnac.network.character.DefaultCharacterNetworkSource
import com.keetr.comicsnac.network.common.DefaultRandomNetworkSource
import com.keetr.comicsnac.network.common.RandomNetworkSource
import com.keetr.comicsnac.network.concept.ConceptNetworkSource
import com.keetr.comicsnac.network.concept.DefaultConceptNetworkSource
import com.keetr.comicsnac.network.createClient
import com.keetr.comicsnac.network.episode.DefaultEpisodeNetworkSource
import com.keetr.comicsnac.network.episode.EpisodeNetworkSource
import com.keetr.comicsnac.network.issue.DefaultIssueNetworkSource
import com.keetr.comicsnac.network.issue.IssueNetworkSource
import com.keetr.comicsnac.network.location.DefaultLocationNetworkSource
import com.keetr.comicsnac.network.location.LocationNetworkSource
import com.keetr.comicsnac.network.movie.DefaultMovieNetworkSource
import com.keetr.comicsnac.network.movie.MovieNetworkSource
import com.keetr.comicsnac.network.`object`.DefaultObjectNetworkSource
import com.keetr.comicsnac.network.`object`.ObjectNetworkSource
import com.keetr.comicsnac.network.origin.DefaultOriginNetworkSource
import com.keetr.comicsnac.network.origin.OriginNetworkSource
import com.keetr.comicsnac.network.person.DefaultPersonNetworkSource
import com.keetr.comicsnac.network.person.PersonNetworkSource
import com.keetr.comicsnac.network.power.DefaultPowerNetworkSource
import com.keetr.comicsnac.network.power.PowerNetworkSource
import com.keetr.comicsnac.network.publisher.DefaultPublisherNetworkSource
import com.keetr.comicsnac.network.publisher.PublisherNetworkSource
import com.keetr.comicsnac.network.search.DefaultSearchNetworkSource
import com.keetr.comicsnac.network.search.SearchNetworkSource
import com.keetr.comicsnac.network.series.DefaultSeriesNetworkSource
import com.keetr.comicsnac.network.series.SeriesNetworkSource
import com.keetr.comicsnac.network.storyarc.DefaultStoryArcNetworkSource
import com.keetr.comicsnac.network.storyarc.StoryArcNetworkSource
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
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClientEngine(): HttpClientEngine = CIO.create()

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
    fun provideEpisodeNetworkSource(client: HttpClient): EpisodeNetworkSource =
        DefaultEpisodeNetworkSource(client)

    @Provides
    @Singleton
    fun provideIssueNetworkSource(client: HttpClient): IssueNetworkSource =
        DefaultIssueNetworkSource(client)

    @Provides
    @Singleton
    fun provideLocationNetworkSource(client: HttpClient): LocationNetworkSource =
        DefaultLocationNetworkSource(client)

    @Provides
    @Singleton
    fun provideMovieNetworkSource(client: HttpClient): MovieNetworkSource =
        DefaultMovieNetworkSource(client)

    @Provides
    @Singleton
    fun provideObjectNetworkSource(client: HttpClient): ObjectNetworkSource =
        DefaultObjectNetworkSource(client)

    @Provides
    @Singleton
    fun provideOriginNetworkSource(client: HttpClient): OriginNetworkSource =
        DefaultOriginNetworkSource(client)

    @Provides
    @Singleton
    fun providePersonNetworkSource(client: HttpClient): PersonNetworkSource =
        DefaultPersonNetworkSource(client)

    @Provides
    @Singleton
    fun providePowerNetworkSource(client: HttpClient): PowerNetworkSource =
        DefaultPowerNetworkSource(client)

    @Provides
    @Singleton
    fun providePublisherNetworkSource(client: HttpClient): PublisherNetworkSource =
        DefaultPublisherNetworkSource(client)

    @Provides
    @Singleton
    fun provideSearchNetworkSource(client: HttpClient): SearchNetworkSource =
        DefaultSearchNetworkSource(client)

    @Provides
    @Singleton
    fun provideSeriesNetworkSource(client: HttpClient): SeriesNetworkSource =
        DefaultSeriesNetworkSource(client)

    @Provides
    @Singleton
    fun provideStoryArcNetworkSource(client: HttpClient): StoryArcNetworkSource =
        DefaultStoryArcNetworkSource(client)

    @Provides
    @Singleton
    fun provideTeamNetworkSource(client: HttpClient): TeamNetworkSource =
        DefaultTeamNetworkSource(client)

    @Provides
    @Singleton
    fun provideVolumeNetworkSource(client: HttpClient): VolumeNetworkSource =
        DefaultVolumeNetworkSource(client)

    @Provides
    @Singleton
    fun provideRandomNetworkSource(client: HttpClient): RandomNetworkSource =
        DefaultRandomNetworkSource(client)
}