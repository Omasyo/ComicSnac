package com.keetr.comicsnac.model

sealed interface RepositoryResponse<out T> {
    data class Success<T>(val content: T) : RepositoryResponse<T>

    sealed class Error(open val message: String) : RepositoryResponse<Nothing>

    sealed class NetworkError(message: String) : Error(message)

    data object InvalidApiKeyError : NetworkError("Invalid Api Key")

    data object TimeoutError : NetworkError("Network Timeout")

    data class UnknownNetworkError(override val message: String) : NetworkError(message)
}


 inline fun <R, T> RepositoryResponse<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (error: RepositoryResponse.Error) -> R
): R {
    return when (this) {
        is RepositoryResponse.Success -> onSuccess(this.content)
        is RepositoryResponse.Error -> onFailure(this)
    }
}
