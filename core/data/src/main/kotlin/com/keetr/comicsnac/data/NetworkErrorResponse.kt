package com.keetr.comicsnac.data

import com.keetr.comicsnac.model.Response
import com.keetr.comicsnac.network.InvalidApiException
import com.keetr.comicsnac.network.InvalidFilterException
import com.keetr.comicsnac.network.InvalidUrlFormatException
import com.keetr.comicsnac.network.ObjectNotFoundException
import com.keetr.comicsnac.network.TimeoutException


fun fromNetworkError(throwable: Throwable): Response.Error = when (throwable) {
    InvalidApiException -> Response.InvalidApiKeyError
    ObjectNotFoundException -> Response.UnknownNetworkError("Object not found")
    InvalidUrlFormatException -> Response.UnknownNetworkError("Invalid Url Format")
    InvalidFilterException -> Response.UnknownNetworkError("Filter Error")
    TimeoutException -> Response.TimeoutError
    else -> Response.UnknownNetworkError(throwable.message ?: "")
}