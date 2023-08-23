package com.keetr.comicsnac.data.di

import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.issue.DefaultIssueRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.team.DefaultTeamRepository
import com.keetr.comicsnac.data.team.TeamRepository
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
    fun bindTeamRepository(teamRepository: DefaultTeamRepository): TeamRepository
}
