package com.keetr.comicsnac.categories.team

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.model.team.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class TeamViewModel @Inject constructor(
    teamRepository: TeamRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Team>(settingsRepository) {

    override val items =
        teamRepository.getAllTeams().cachedIn(viewModelScope)

}

