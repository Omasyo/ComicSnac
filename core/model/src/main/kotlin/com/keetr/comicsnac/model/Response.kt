package com.keetr.comicsnac.model

sealed interface Response<out T> {
    data class Success<T>(val content: T) : Response<T>

    sealed class Error(open val message: String) : Response<Nothing>

    sealed class NetworkError(message: String) : Error(message)

    data object InvalidApiKeyError : NetworkError("Invalid Api Key")

    data object TimeoutError : NetworkError("Network Timeout")

    data class UnknownNetworkError(override val message: String) : NetworkError(message)
}