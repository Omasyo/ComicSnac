package com.keetr.comicsnac.categories.volume

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.model.volume.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class VolumeViewModel @Inject constructor(
    volumeRepository: VolumeRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Volume>(settingsRepository) {

    override val items =
        volumeRepository.getAllVolumes().cachedIn(viewModelScope)

}

