package com.keetr.comicsnac.categories.publisher

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.publisher.PublisherRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.publisher.Publisher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PublisherViewModel @Inject constructor(
    publisherRepository: PublisherRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Publisher>(settingsRepository) {

    override val items =
        publisherRepository.getAllPublishers().cachedIn(viewModelScope)

}

