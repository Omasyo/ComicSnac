package com.keetr.comicsnac.network

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.File

fun createClient(engine: HttpClientEngine, context: Context) =
    HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        install(HttpCache) {
            val cacheFile = File(context.cacheDir, "comicsnac-ktor-cache")
            publicStorage(FileStorage(cacheFile))
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = Api.Host
                path(Api.DefaultPath)
                parameters.append("api_key", ApiKey)
                parameters.append("format", Api.ResponseFormat)
            }
        }
    }


//suspend fun main() {
//    val client = createClient(CIO.create())
//
//    val t: CharactersDetailsResponseApiModel = client.get("https://comicvine.gamespot.com/api/character/4005-1699/"){
//        appendDefaultParameters()
//    }.body()
//
//    println(t)
//}