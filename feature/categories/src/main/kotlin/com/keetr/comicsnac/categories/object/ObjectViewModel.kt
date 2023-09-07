package com.keetr.comicsnac.categories.`object`

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.`object`.ObjectRepository
import com.keetr.comicsnac.model.`object`.ObjectItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ObjectViewModel @Inject constructor(
    objectsRepository: ObjectRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<ObjectItem>(settingsRepository) {

    override val items =
        objectsRepository.getAllObjects().cachedIn(viewModelScope)

}

