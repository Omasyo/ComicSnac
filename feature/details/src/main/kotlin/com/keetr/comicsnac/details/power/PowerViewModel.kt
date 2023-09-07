package com.keetr.comicsnac.details.power

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.character.getErrorPagingData
import com.keetr.comicsnac.data.power.PowerRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.character.getState
import com.keetr.comicsnac.model.character.Character
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
internal class PowerViewModel @Inject constructor(
    private val powerRepository: PowerRepository,
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        powerRepository.getPowerDetails(id).map(::getState)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)


    @OptIn(ExperimentalCoroutinesApi::class)
    val characters: Flow<PagingData<Character>> = detailsUiState.flatMapLatest {
        when (it) {
            is Error -> getErrorPagingData<Character>().flow
            Loading -> emptyFlow()
            is Success -> {
                characterRepository.getCharactersWithId(it.content.characterIds)
            }
        }
    }.cachedIn(viewModelScope)
}