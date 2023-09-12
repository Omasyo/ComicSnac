package com.keetr.comicsnac.categories.episode

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.episode.EpisodeRepository
import com.keetr.comicsnac.model.episode.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EpisodeViewModel @Inject constructor(
    episodeRepository: EpisodeRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Episode>(settingsRepository) {

    override val items =
        episodeRepository.getAllEpisodes().cachedIn(viewModelScope)
}

