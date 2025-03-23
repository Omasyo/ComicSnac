package com.keetr.comicsnac.details.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.person.PersonRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.RefreshWrapper
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.volume.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class PersonViewModel @Inject constructor(
    personRepository: PersonRepository,
    private val characterRepository: CharacterRepository,
    private val volumeRepository: VolumeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        RefreshWrapper(viewModelScope) { personRepository.getPersonDetails(id) }.response

    @OptIn(ExperimentalCoroutinesApi::class)
    val characters: Flow<PagingData<Character>> =
        detailsUiState.flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    characterRepository.getCharactersWithId(it.content.createdCharactersId)
                }
            }
        }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val volumes: Flow<PagingData<Volume>> =
        detailsUiState.flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    volumeRepository.getVolumesWithId(it.content.volumesId)
                }
            }
        }.cachedIn(viewModelScope)
}