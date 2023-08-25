package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.model.team.TeamDetails


internal sealed interface DetailsUiState<out T>

internal data object Loading : DetailsUiState<Nothing>

internal data class Error(val error: RepositoryResponse.Error) : DetailsUiState<Nothing>

@Immutable
internal data class Success<T>(val content: T) : DetailsUiState<T>

internal typealias CharacterDetailsUiState = DetailsUiState<CharacterDetails>

internal typealias IssueDetailsUiState = DetailsUiState<IssueDetails>

internal typealias TeamDetailsUiState = DetailsUiState<TeamDetails>