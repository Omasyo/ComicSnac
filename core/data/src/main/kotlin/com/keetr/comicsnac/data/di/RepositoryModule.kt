package com.keetr.comicsnac.data.di

import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.concept.ConceptRepository
import com.keetr.comicsnac.data.concept.DefaultConceptRepository
import com.keetr.comicsnac.data.episode.DefaultEpisodeRepository
import com.keetr.comicsnac.data.episode.EpisodeRepository
import com.keetr.comicsnac.data.issue.DefaultIssueRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.location.DefaultLocationRepository
import com.keetr.comicsnac.data.location.LocationRepository
import com.keetr.comicsnac.data.movie.DefaultMovieRepository
import com.keetr.comicsnac.data.movie.MovieRepository
import com.keetr.comicsnac.data.`object`.DefaultObjectRepository
import com.keetr.comicsnac.data.`object`.ObjectRepository
import com.keetr.comicsnac.data.origin.DefaultOriginRepository
import com.keetr.comicsnac.data.origin.OriginRepository
import com.keetr.comicsnac.data.person.DefaultPersonRepository
import com.keetr.comicsnac.data.person.PersonRepository
import com.keetr.comicsnac.data.power.DefaultPowerRepository
import com.keetr.comicsnac.data.power.PowerRepository
import com.keetr.comicsnac.data.publisher.DefaultPublisherRepository
import com.keetr.comicsnac.data.publisher.PublisherRepository
import com.keetr.comicsnac.data.search.DefaultSearchRepository
import com.keetr.comicsnac.data.search.SearchRepository
import com.keetr.comicsnac.data.series.DefaultSeriesRepository
import com.keetr.comicsnac.data.series.SeriesRepository
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.data.settings.DefaultAuthRepository
import com.keetr.comicsnac.data.settings.DefaultSettingsRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.storyarc.DefaultStoryArcRepository
import com.keetr.comicsnac.data.storyarc.StoryArcRepository
import com.keetr.comicsnac.data.team.DefaultTeamRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.data.volume.DefaultVolumeRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
private interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(characterRepository: DefaultCharacterRepository): CharacterRepository

    @Binds
    fun bindConceptRepository(conceptRepository: DefaultConceptRepository): ConceptRepository

    @Binds
    fun bindEpisodeRepository(episodeRepository: DefaultEpisodeRepository): EpisodeRepository

    @Binds
    fun bindIssueRepository(issueRepository: DefaultIssueRepository): IssueRepository

    @Binds
    fun bindLocationRepository(locationRepository: DefaultLocationRepository): LocationRepository

    @Binds
    fun bindMovieRepository(movieRepository: DefaultMovieRepository): MovieRepository

    @Binds
    fun bindObjectRepository(objectRepository: DefaultObjectRepository): ObjectRepository

    @Binds
    fun bindOriginRepository(originRepository: DefaultOriginRepository): OriginRepository

    @Binds
    fun bindPersonRepository(personRepository: DefaultPersonRepository): PersonRepository

    @Binds
    fun bindPowerRepository(powerRepository: DefaultPowerRepository): PowerRepository

    @Binds
    fun bindPublisherRepository(publisherRepository: DefaultPublisherRepository): PublisherRepository

    @Binds
    fun bindSearchRepository(searchRepository: DefaultSearchRepository): SearchRepository

    @Binds
    fun bindSeriesRepository(seriesRepository: DefaultSeriesRepository): SeriesRepository

    @Binds
    fun bindStoryArcRepository(storyArcRepository: DefaultStoryArcRepository): StoryArcRepository

    @Binds
    fun bindTeamRepository(teamRepository: DefaultTeamRepository): TeamRepository

    @Binds
    fun bindVolumeRepository(volumeRepository: DefaultVolumeRepository): VolumeRepository

    @Binds
    fun bindSettingsRepository(settingsRepository: DefaultSettingsRepository): SettingsRepository

    @Binds
    fun bindAuthRepository(authRepository: DefaultAuthRepository): AuthRepository
}
