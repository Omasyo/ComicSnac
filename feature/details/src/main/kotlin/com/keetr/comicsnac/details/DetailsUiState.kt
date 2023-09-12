package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse


internal sealed interface DetailsUiState<out T>

internal data object Loading : DetailsUiState<Nothing>

internal data class Error(val error: RepositoryResponse.Error) : DetailsUiState<Nothing>

@Immutable
internal data class Success<T>(val content: T) : DetailsUiState<T>

internal fun <T> getState(response: RepositoryResponse<T>) =
    when (response) {
        is RepositoryResponse.Error -> Error(response)
        is RepositoryResponse.Success -> Success(response.content)
    }