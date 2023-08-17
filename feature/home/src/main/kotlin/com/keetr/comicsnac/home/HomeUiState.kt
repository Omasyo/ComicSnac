package com.keetr.comicsnac.home

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.publisher.Publisher
import com.keetr.comicsnac.model.series.Series
import com.keetr.comicsnac.model.volume.Volume

sealed interface HomeCategoryUiState<out T>

data object InDevelopment : HomeCategoryUiState<Nothing>

data object Loading : HomeCategoryUiState<Nothing>

data class Error(val error: RepositoryResponse.Error) : HomeCategoryUiState<Nothing>

@Immutable
data class Success<T>(val contents: List<T>) : HomeCategoryUiState<T>

typealias IssuesUiState = HomeCategoryUiState<Issue>

typealias CharactersUiState = HomeCategoryUiState<Character>

typealias VolumesUiState = HomeCategoryUiState<Volume>

typealias MoviesUiState = HomeCategoryUiState<Movie>

typealias SeriesUiState = HomeCategoryUiState<Series>

typealias PublishersUiState = HomeCategoryUiState<Publisher>

//@Stable // TODO: Necessary?
data class HomeUiState(
    val issuesUiState: IssuesUiState,
    val charactersUiState: CharactersUiState,
    val volumesUiState: VolumesUiState,
    val moviesUiState: MoviesUiState,
    val seriesUiState: SeriesUiState,
    val publishersUiState: PublishersUiState,
)
