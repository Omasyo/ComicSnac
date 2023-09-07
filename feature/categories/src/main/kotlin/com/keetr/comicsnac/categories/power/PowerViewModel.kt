package com.keetr.comicsnac.categories.power

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.power.PowerRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.power.PowerBasic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PowerViewModel @Inject constructor(
    powerRepository: PowerRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<PowerBasic>(settingsRepository) {

    override val items =
        powerRepository.getAllPowers().cachedIn(viewModelScope)

}

