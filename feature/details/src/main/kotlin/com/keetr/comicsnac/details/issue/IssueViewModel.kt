package com.keetr.comicsnac.details.issue

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.team.TeamRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.RefreshWrapper
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.team.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class IssueViewModel @Inject constructor(
    issueRepository: IssueRepository,
    private val characterRepository: CharacterRepository,
    private val teamRepository: TeamRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        RefreshWrapper(viewModelScope) { issueRepository.getIssueDetails(id) }.response


    val characters: Flow<PagingData<Character>> = getPagingData {
        characterRepository.getCharactersWithId(charactersId)
    }

    val locations: Flow<PagingData<Location>> = getPagingData {
        emptyFlow()
    }

    val objects: Flow<PagingData<ObjectItem>> = getPagingData {
        emptyFlow()
    }

    val storyArcs: Flow<PagingData<StoryArc>> = getPagingData {
        emptyFlow()
    }

    val teams: Flow<PagingData<Team>> = getPagingData {
        teamRepository.getTeamsWithId(teamsId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun <T : Any> getPagingData(init: IssueDetails.() -> Flow<PagingData<T>>) =
        detailsUiState.flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    init(it.content)
                }
            }
        }.cachedIn(viewModelScope)
}