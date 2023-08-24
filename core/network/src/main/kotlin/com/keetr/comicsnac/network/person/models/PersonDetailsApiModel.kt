package com.keetr.comicsnac.network.person.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import com.keetr.comicsnac.network.common.serializers.DateAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PersonDetailsApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,

    @Serializable(DateAsStringSerializer::class)
    @SerialName("birth") val birth: LocalDateTime,

    @SerialName("created_characters") val createdCharacters: List<CharacterApiModel>,
    @SerialName("death") val death: DeathApiModel,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String,
    @SerialName("email") val email: String?,
    @SerialName("gender") val gender: Int,
    @SerialName("hometown") val hometown: String,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("volume_credits") val volumeCredits: List<VolumeApiModel>,
    @SerialName("website") val website: String?
)