package com.keetr.comicsnac.home

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue

@Immutable
sealed interface HomeCategoryUiState<out T>

data object InDevelopment : HomeCategoryUiState<Nothing>

data object Loading : HomeCategoryUiState<Nothing>

data class Error(val error: RepositoryResponse.Error) : HomeCategoryUiState<Nothing>

data class Success<T>(val contents: List<T>) : HomeCategoryUiState<T>

typealias IssuesUiState = HomeCategoryUiState<Issue>

typealias CharactersUiState = HomeCategoryUiState<Character>

typealias VolumesUiState = HomeCategoryUiState<Nothing>

typealias MoviesUiState = HomeCategoryUiState<Nothing>

typealias SeriesUiState = HomeCategoryUiState<Nothing>

typealias PublishersUiState = HomeCategoryUiState<Nothing>

//@Stable // TODO: Necessary?
data class HomeUiState(
    val issuesUiState: IssuesUiState,
    val charactersUiState: CharactersUiState,
    val volumesUiState: VolumesUiState,
    val moviesUiState: MoviesUiState,
    val seriesUiState: SeriesUiState,
    val publishersUiState: PublishersUiState,
)
