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
import com.keetr.comicsnac.details.RefreshWrapper
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.model.issue.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class VolumeViewModel @Inject constructor(
    volumeRepository: VolumeRepository,
    private val issueRepository: IssueRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        RefreshWrapper(viewModelScope) { volumeRepository.getVolumeDetails(id) }.response


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