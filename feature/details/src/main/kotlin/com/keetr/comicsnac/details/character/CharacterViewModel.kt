package com.keetr.comicsnac.details.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        characterRepository.getCharacterDetails(id).map(::getState).stateInCurrentScope()

    private fun <T> Flow<DetailsUiState<T>>.stateInCurrentScope() =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}


private fun <T> getState(response: RepositoryResponse<T>) =
    when (response) {
        is RepositoryResponse.Error -> Error(response)
        is RepositoryResponse.Success -> Success(response.content)
    }
