package com.keetr.comicsnac.network.origin.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginDetailsApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("characters") val characters: List<CharacterApiModel>,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)