package com.keetr.comicsnac.details.storyarc

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.episode.EpisodeRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.storyarc.StoryArcRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.RefreshWrapper
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.model.episode.Episode
import com.keetr.comicsnac.model.issue.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class StoryArcViewModel @Inject constructor(
    storyArcRepository: StoryArcRepository,
    private val episodeRepository: EpisodeRepository,
    private val issueRepository: IssueRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        RefreshWrapper(viewModelScope) { storyArcRepository.getStoryArcDetails(id) }.response

    val episodes: Flow<PagingData<Episode>> = detailsUiState.flatMapLatest {
        when (it) {
            is Error -> emptyFlow()
            Loading -> emptyFlow()
            is Success -> {
                episodeRepository.getEpisodesWithId(it.content.episodesId)
            }
        }
    }.cachedIn(viewModelScope)

    val issues: Flow<PagingData<Issue>> = detailsUiState.flatMapLatest {
        when (it) {
            is Error -> emptyFlow()
            Loading -> emptyFlow()
            is Success -> {
                issueRepository.getIssuesWithId(it.content.issuesId)
            }
        }
    }.cachedIn(viewModelScope)
}