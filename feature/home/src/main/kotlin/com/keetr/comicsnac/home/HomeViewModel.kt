package com.keetr.comicsnac.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.issue.DefaultIssueRepository
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val issueRepository: DefaultIssueRepository,
) : ViewModel() {
    private val _characterUiState = MutableStateFlow<CharactersUiState>(Loading)
    val charactersUiState = _characterUiState.asStateFlow()

    private val _issueUiState = MutableStateFlow<IssuesUiState>(Loading)
    val issueUiState = _issueUiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                _characterUiState.value =
                    getCategoryState(characterRepository.getRecentCharacters())
            }
            launch { _issueUiState.value = getCategoryState(issueRepository.getRecentIssues()) }
        }
    }

    private fun <T> getCategoryState(response: RepositoryResponse<List<T>>) =
        when (response) {
            is RepositoryResponse.Error -> Error(response)
            is RepositoryResponse.Success -> Success(response.content)
        }
}