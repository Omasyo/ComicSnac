package com.keetr.comicsnac.categories.volume

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.model.volume.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class VolumeViewModel @Inject constructor(
    volumeRepository: VolumeRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Volume>(settingsRepository) {

    override val items =
        volumeRepository.getAllVolumes().cachedIn(viewModelScope)

}

