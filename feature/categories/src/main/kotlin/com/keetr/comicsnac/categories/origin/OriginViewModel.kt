package com.keetr.comicsnac.categories.origin

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.origin.OriginRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.origin.OriginBasic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OriginViewModel @Inject constructor(
    originRepository: OriginRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<OriginBasic>(settingsRepository) {

    override val items =
        originRepository.getAllOrigins().cachedIn(viewModelScope)

}

