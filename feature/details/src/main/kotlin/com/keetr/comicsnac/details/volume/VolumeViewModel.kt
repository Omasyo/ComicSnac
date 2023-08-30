package com.keetr.comicsnac.details.volume

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.character.getState
import com.keetr.comicsnac.model.issue.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class VolumeViewModel @Inject constructor(
    private val volumeRepository: VolumeRepository,
    private val issueRepository: IssueRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        volumeRepository.getVolumeDetails(id).map(::getState)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)


    @OptIn(ExperimentalCoroutinesApi::class)
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