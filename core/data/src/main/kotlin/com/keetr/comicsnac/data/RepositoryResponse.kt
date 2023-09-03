package com.keetr.comicsnac.data

import com.keetr.comicsnac.network.InvalidApiException
import com.keetr.comicsnac.network.InvalidFilterException
import com.keetr.comicsnac.network.InvalidUrlFormatException
import com.keetr.comicsnac.network.ObjectNotFoundException
import com.keetr.comicsnac.network.TimeoutException

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

internal fun fromNetworkError(throwable: Throwable): RepositoryResponse.Error = when (throwable) {
    InvalidApiException -> RepositoryResponse.InvalidApiKeyError
    ObjectNotFoundException -> RepositoryResponse.UnknownNetworkError("Object not found")
    InvalidUrlFormatException -> RepositoryResponse.UnknownNetworkError("Invalid Url Format")
    InvalidFilterException -> RepositoryResponse.UnknownNetworkError("Filter Error")
    TimeoutException -> RepositoryResponse.TimeoutError
    else -> RepositoryResponse.UnknownNetworkError(throwable.message ?: "")
}
