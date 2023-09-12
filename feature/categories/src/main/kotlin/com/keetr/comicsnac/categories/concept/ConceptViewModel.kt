package com.keetr.comicsnac.categories.concept

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.concept.ConceptRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.other.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class ConceptViewModel @Inject constructor(
    conceptRepository: ConceptRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Concept>(settingsRepository) {

    override val items =
        conceptRepository.getAllConcepts().cachedIn(viewModelScope)

}

