package com.keetr.comicsnac.details.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.movie.MovieRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.getState
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.volume.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val movieRepository: MovieRepository,
    private val teamRepository: TeamRepository,
    private val volumeRepository: VolumeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        characterRepository.getCharacterDetails(id).map(::getState).stateInCurrentScope()


    val enemies: Flow<PagingData<Character>> = getPagingData {
        characterRepository.getCharactersWithId(enemiesId)
    }

    val friends: Flow<PagingData<Character>> = getPagingData {
        characterRepository.getCharactersWithId(friendsId)
    }

    val movies: Flow<PagingData<Movie>> = getPagingData {
        movieRepository.getMoviesWithId(moviesId)
    }

    val teams: Flow<PagingData<Team>> = getPagingData {
        teamRepository.getTeamsWithId(teamsId)
    }

    val teamEnemies: Flow<PagingData<Team>> = getPagingData {
        teamRepository.getTeamsWithId(teamEnemiesId)
    }

    val teamFriends: Flow<PagingData<Team>> = getPagingData {
        teamRepository.getTeamsWithId(teamFriendsId)
    }

    val volumes: Flow<PagingData<Volume>> = getPagingData {
        volumeRepository.getVolumesWithId(volumeCreditsId)
    }

    private fun <T> Flow<DetailsUiState<T>>.stateInCurrentScope() =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun <T : Any> getPagingData(init: CharacterDetails.() -> Flow<PagingData<T>>) =
        detailsUiState.flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    init(it.content)
                }
            }
        }.cachedIn(viewModelScope)
}
