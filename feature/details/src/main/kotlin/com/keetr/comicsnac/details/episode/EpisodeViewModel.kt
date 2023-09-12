package com.keetr.comicsnac.details.episode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.episode.EpisodeRepository
import com.keetr.comicsnac.data.location.LocationRepository
import com.keetr.comicsnac.data.`object`.ObjectRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.getState
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.episode.EpisodeDetails
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.team.Team
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
internal class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
    private val objectRepository: ObjectRepository,
    private val teamRepository: TeamRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        episodeRepository.getEpisodeDetails(id).map(::getState).stateInCurrentScope()


    val characters: Flow<PagingData<Character>> = getPagingData {
        characterRepository.getCharactersWithId(charactersId)
    }

    val locations: Flow<PagingData<Location>> = getPagingData {
        locationRepository.getLocationsWithId(locationsId)
    }

    val objects: Flow<PagingData<ObjectItem>> = getPagingData {
        objectRepository.getObjectsWithId(objectsId)
    }

    val teams: Flow<PagingData<Team>> = getPagingData {
        teamRepository.getTeamsWithId(teamsId)
    }

    private fun <T> Flow<DetailsUiState<T>>.stateInCurrentScope() =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun <T : Any> getPagingData(init: EpisodeDetails.() -> Flow<PagingData<T>>) =
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