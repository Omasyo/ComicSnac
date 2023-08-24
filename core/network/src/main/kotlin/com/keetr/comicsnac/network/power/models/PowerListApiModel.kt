package com.keetr.comicsnac.network.power.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)