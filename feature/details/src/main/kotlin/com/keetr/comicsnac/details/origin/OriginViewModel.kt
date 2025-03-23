package com.keetr.comicsnac.details.origin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.character.getErrorPagingData
import com.keetr.comicsnac.data.origin.OriginRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.RefreshWrapper
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.model.character.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class OriginViewModel @Inject constructor(
    originRepository: OriginRepository,
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        RefreshWrapper(viewModelScope) { originRepository.getOriginDetails(id) }.response


    @OptIn(ExperimentalCoroutinesApi::class)
    val characters: Flow<PagingData<Character>> = detailsUiState.flatMapLatest {
        when (it) {
            is Error -> getErrorPagingData<Character>().flow
            Loading -> emptyFlow()
            is Success -> {
                characterRepository.getCharactersWithId(it.content.charactersId)
            }
        }
    }.cachedIn(viewModelScope)
}