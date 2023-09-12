package com.keetr.comicsnac.details.location

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.location.LocationRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.getState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        locationRepository.getLocationDetails(id).map(::getState)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}

