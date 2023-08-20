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
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val issueRepository: IssueRepository,
) : ViewModel() {
//    private val _characterUiState: MutableStateFlow<CharactersUiState> =

    val charactersUiState =
        characterRepository.getRecentCharacters().map(::getCategoryState).stateInCurrentScope()

    private val _issueUiState = MutableStateFlow<IssuesUiState>(Loading)
    val issueUiState =
        issueRepository.getRecentIssues().map(::getCategoryState).stateInCurrentScope()

//    init {
//
//        viewModelScope.launch {
////            launch {
////                _characterUiState.value =
////                    getCategoryState(characterRepository.getRecentCharacters())
////            }
//            launch { _issueUiState.value = getCategoryState(issueRepository.getRecentIssues()) }
//        }
//    }

    private fun <T> getCategoryState(response: RepositoryResponse<List<T>>) =
        when (response) {
            is RepositoryResponse.Error -> Error(response)
            is RepositoryResponse.Success -> Success(response.content)
        }

    private fun<T> Flow<HomeCategoryUiState<T>>.stateInCurrentScope() =
    stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}