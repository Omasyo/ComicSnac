package com.keetr.comicsnac.data.di

import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.issue.DefaultIssueRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.power.DefaultPowerRepository
import com.keetr.comicsnac.data.power.PowerRepository
import com.keetr.comicsnac.data.search.DefaultSearchRepository
import com.keetr.comicsnac.data.search.SearchRepository
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
    fun bindMovieRepository(characterRepository: DefaultCharacterRepository): CharacterRepository

    @Binds
    fun bindIssueRepository(issueRepository: DefaultIssueRepository): IssueRepository

    @Binds
    fun bindPowerRepository(powerRepository: DefaultPowerRepository): PowerRepository

    @Binds
    fun bindSearchRepository(searchRepository: DefaultSearchRepository): SearchRepository

    @Binds
    fun bindTeamRepository(teamRepository: DefaultTeamRepository): TeamRepository

    @Binds
    fun bindVolumeRepository(volumeRepository: DefaultVolumeRepository): VolumeRepository
}
