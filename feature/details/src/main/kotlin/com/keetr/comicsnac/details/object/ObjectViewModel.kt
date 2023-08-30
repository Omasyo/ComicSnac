package com.keetr.comicsnac.details.`object`

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.`object`.ObjectRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.character.getState
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.team.TeamDetails
import com.keetr.comicsnac.model.volume.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class ObjectViewModel @Inject constructor(
    private val objectRepository: ObjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        objectRepository.getObjectDetails(id).map(::getState)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}