package com.keetr.comicsnac.network.character.models

import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.serializers.DateAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CharacterApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("date_last_updated")
    @Serializable(DateAsStringSerializer::class) val dateLastUpdate: LocalDateTime,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("gender") val gender: GenderApiModel,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)