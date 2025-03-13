package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse


internal sealed interface DetailsUiState<out T> {
    val refresh: () -> Unit
}

internal data object Loading : DetailsUiState<Nothing> {
    override val refresh: () -> Unit = {}
}

internal data class Error(val error: RepositoryResponse.Error, override val refresh: () -> Unit) :
    DetailsUiState<Nothing> {

    @Deprecated("")
    constructor(error: RepositoryResponse.Error) : this(error, {})
}

@Immutable
internal data class Success<T>(val content: T, override val refresh: () -> Unit = {}) :
    DetailsUiState<T>

internal fun <T> getState(response: RepositoryResponse<T>) =
    when (response) {
        is RepositoryResponse.Error -> Error(response, {})
        is RepositoryResponse.Success -> Success(response.content)
    }