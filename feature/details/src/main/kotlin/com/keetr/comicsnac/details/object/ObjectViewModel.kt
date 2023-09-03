package com.keetr.comicsnac.details.`object`

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.`object`.ObjectRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.character.getState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class ObjectViewModel @Inject constructor(
    objectRepository: ObjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        objectRepository.getObjectDetails(id).map(::getState)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}