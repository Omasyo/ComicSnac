package com.keetr.comicsnac.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

object Api {
    const val Host = "comicvine.gamespot.com"
    const val DefaultPath = "api/"

    const val ResponseFormat = "json"
    fun HttpRequestBuilder.appendDefaultParameters() {
        parameter("api_key", ApiKey)
        parameter("format", ResponseFormat)
    }
}