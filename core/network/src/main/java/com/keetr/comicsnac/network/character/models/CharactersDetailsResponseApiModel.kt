package com.keetr.comicsnac.network.character.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersDetailsResponseApiModel<T>(
    @SerialName("error") val error: String,
    @SerialName("limit") val limit: Int,
    @SerialName("number_of_page_results") val numberOfPageResults: Int,
    @SerialName("number_of_total_results") val numberOfTotalResults: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("results") val results: T,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("version") val version: String
)