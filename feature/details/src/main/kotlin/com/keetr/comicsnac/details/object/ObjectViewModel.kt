package com.keetr.comicsnac.details.`object`

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.`object`.ObjectRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.RefreshWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ObjectViewModel @Inject constructor(
    objectRepository: ObjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        RefreshWrapper(viewModelScope) { objectRepository.getObjectDetails(id) }.response
}