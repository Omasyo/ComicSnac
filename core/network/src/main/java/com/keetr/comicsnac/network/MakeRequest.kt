package com.keetr.comicsnac.network

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.Response

object InvalidApiException: Throwable()
suspend inline fun <reified T> makeRequest(exec: () -> HttpResponse): Result<ResponseApiModel<T>> =
    try {
        val response = exec()
        if (response.status == HttpStatusCode.OK) {
            val content: ResponseApiModel<T> = response.body()

            when(content.statusCode) {
                1 -> Result.success(content)
                100 -> Result.failure(InvalidApiException)
                else -> Result.failure(Exception(content.error))
            }
        } else {
            Result.failure(Exception(response.status.description))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }