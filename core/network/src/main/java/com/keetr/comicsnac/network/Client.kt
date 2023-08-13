package com.keetr.comicsnac.network

import android.content.Context
import com.keetr.comicsnac.network.character.models.CharactersDetailsResponseApiModel
import com.keetr.comicsnac.network.character.models.ResultsApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.File

const val ResponseFormat = "json"

fun createClient(engine: HttpClientEngine, /*context: Context*/) =
    HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
//        install(HttpCache) {
//            val cacheFile = File(context.cacheDir, "comicsnac-ktor-cache")
//            publicStorage(FileStorage(cacheFile))
//        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "comicvine.gamespot.com"
                path("api/")
                parameters.append("api_key", ApiKey)
                parameters.append("format", "json")
            }
        }
    }


suspend fun main() {
    val client = createClient(CIO.create())

    val t: CharactersDetailsResponseApiModel<ResultsApiModel> = client.get("https://comicvine.gamespot.com/api/character/4005-1699/"){
        parameter("api_key", ApiKey)
        parameter("format", ResponseFormat)
    }.body()

    println(t)
}