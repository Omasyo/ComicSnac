package com.keetr.comicsnac.categories.team

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.model.team.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class TeamViewModel @Inject constructor(
    teamRepository: TeamRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Team>(settingsRepository) {

    override val items =
        teamRepository.getAllTeams().cachedIn(viewModelScope)

}

