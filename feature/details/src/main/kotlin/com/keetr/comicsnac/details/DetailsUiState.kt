package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.character.CharacterDetails


internal sealed interface DetailsUiState<out T>

internal data object InDevelopment : DetailsUiState<Nothing>

internal data object Loading : DetailsUiState<Nothing>

internal data class Error(val error: RepositoryResponse.Error) : DetailsUiState<Nothing>

@Immutable
internal data class Success<T>(val content: T) : DetailsUiState<T>

internal typealias CharacterDetailsUiState = DetailsUiState<CharacterDetails>