package com.keetr.comicsnac.categories.series

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.series.SeriesRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.series.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SeriesViewModel @Inject constructor(
    seriesRepository: SeriesRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Series>(settingsRepository) {

    override val items =
        seriesRepository.getAllSeries().cachedIn(viewModelScope)

}

