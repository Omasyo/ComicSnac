package com.keetr.comicsnac.categories.locations

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.location.LocationRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.other.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class LocationViewModel @Inject constructor(
    locationRepository: LocationRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Location>(settingsRepository) {

    override val items =
        locationRepository.getAllLocations().cachedIn(viewModelScope)

}

