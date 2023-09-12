package com.keetr.comicsnac.categories.storyarcs

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.storyarc.StoryArcRepository
import com.keetr.comicsnac.model.storyarc.StoryArc
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class StoryArcViewModel @Inject constructor(
    storyArcRepository: StoryArcRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<StoryArc>(settingsRepository) {

    override val items =
        storyArcRepository.getAllStoryArcs().cachedIn(viewModelScope)

}

