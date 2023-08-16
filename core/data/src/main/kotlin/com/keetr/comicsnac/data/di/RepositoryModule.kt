package com.keetr.comicsnac.data.di

import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
private interface RepositoryModule {
    @Binds
    fun bindMovieRepository(movieRepository: DefaultCharacterRepository): CharacterRepository
}
