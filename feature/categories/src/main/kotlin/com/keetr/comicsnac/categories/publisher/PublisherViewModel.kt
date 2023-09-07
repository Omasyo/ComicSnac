package com.keetr.comicsnac.categories.publisher

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.publisher.PublisherRepository
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.model.publisher.Publisher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class PublisherViewModel @Inject constructor(
    publisherRepository: PublisherRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Publisher>(settingsRepository) {

    override val items =
        publisherRepository.getAllPublishers().cachedIn(viewModelScope)

}

