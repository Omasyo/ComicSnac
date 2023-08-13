package com.keetr.comicsnac.network.character.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class CharactersListResponse<T> {
    @SerialName("error")
    abstract val error: String

    @SerialName("limit")
    abstract val limit: Int

    @SerialName("number_of_page_results")
    abstract val numberOfPageResults: Int

    @SerialName("number_of_total_results")
    abstract val numberOfTotalResults: Int

    @SerialName("offset")
    abstract val offset: Int

    @SerialName("results")
    abstract val results: List<T>

    @SerialName("status_code")
    abstract val statusCode: Int

    @SerialName("version")
    abstract val version: String
}