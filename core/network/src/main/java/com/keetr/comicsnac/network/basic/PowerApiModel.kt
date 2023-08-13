package com.keetr.comicsnac.network.basic


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)