package com.keetr.comicsnac.network.common.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)