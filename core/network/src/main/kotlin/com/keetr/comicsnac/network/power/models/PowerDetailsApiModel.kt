package com.keetr.comicsnac.network.power.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerDetailsApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("characters") val characters: List<CharacterApiModel>,
    @SerialName("description") val description: String?,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)