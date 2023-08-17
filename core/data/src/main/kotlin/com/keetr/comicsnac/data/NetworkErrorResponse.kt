package com.keetr.comicsnac.data

import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.network.InvalidApiException
import com.keetr.comicsnac.network.InvalidFilterException
import com.keetr.comicsnac.network.InvalidUrlFormatException
import com.keetr.comicsnac.network.ObjectNotFoundException
import com.keetr.comicsnac.network.TimeoutException


fun fromNetworkError(throwable: Throwable): RepositoryResponse.Error = when (throwable) {
    InvalidApiException -> RepositoryResponse.InvalidApiKeyError
    ObjectNotFoundException -> RepositoryResponse.UnknownNetworkError("Object not found")
    InvalidUrlFormatException -> RepositoryResponse.UnknownNetworkError("Invalid Url Format")
    InvalidFilterException -> RepositoryResponse.UnknownNetworkError("Filter Error")
    TimeoutException -> RepositoryResponse.TimeoutError
    else -> RepositoryResponse.UnknownNetworkError(throwable.message ?: "")
}