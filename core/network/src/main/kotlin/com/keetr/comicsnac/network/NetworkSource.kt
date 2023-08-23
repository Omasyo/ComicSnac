package com.keetr.comicsnac.network

import android.util.Log
import com.keetr.comicsnac.network.common.models.ResponseApiModel
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

object InvalidApiException : Throwable()
object ObjectNotFoundException : Throwable()
object InvalidUrlFormatException : Throwable()
object InvalidFilterException : Throwable()
object TimeoutException : Throwable()


interface NetworkSource {
    val tag: String
        get() = javaClass.simpleName
}


internal suspend inline fun <reified T> NetworkSource.makeRequest(
    exec: () -> HttpResponse
): Result<ResponseApiModel<T>> =
    try {
        val response = exec()
        when (response.status) {
            HttpStatusCode.OK -> {
                val content: ResponseApiModel<T> = response.body()

                when (content.statusCode) {
                    1 -> Result.success(content)
                    else -> {
                        Log.w(tag, "makeRequest: ${content.error}")
                        when (content.statusCode) {
                            100 -> Result.failure(InvalidApiException)
                            101 -> Result.failure(ObjectNotFoundException)
                            102 -> Result.failure(InvalidUrlFormatException)
                            104 -> Result.failure(InvalidFilterException)
                            else -> Result.failure(Exception(content.error))
                        }
                    }
                }
            }

            HttpStatusCode.RequestTimeout -> {
                Log.w(tag, "makeRequest: ${response.bodyAsText()}")
                Result.failure(TimeoutException)
            }

            else -> {
                Log.w(tag, "makeRequest: ${response.bodyAsText()}")
                Result.failure(Exception(response.status.description))
            }
        }
    } catch (e: Exception) {
        Log.w(tag, "makeRequest: ${e.message}")
        Result.failure(e)
    }