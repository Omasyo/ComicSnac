package com.keetr.comicsnac.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.issue.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val issueRepository: IssueRepository,
) : ViewModel() {

    val charactersUiState =
        characterRepository.getRecentCharacters().map(::getCategoryState).stateInCurrentScope()

    private val _issueUiState = MutableStateFlow<IssuesUiState>(Loading)
    val issueUiState =
        issueRepository.getRecentIssues().map(::getCategoryState).stateInCurrentScope()


    private fun <T> getCategoryState(response: RepositoryResponse<List<T>>) =
        when (response) {
            is RepositoryResponse.Error -> Error(response)
            is RepositoryResponse.Success -> Success(response.content)
        }

    private fun<T> Flow<HomeCategoryUiState<T>>.stateInCurrentScope() =
    stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}