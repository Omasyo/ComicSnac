package com.keetr.comicsnac.network.character.models

import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.serializers.DateAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CharacterListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("gender") val gender: GenderApiModel,
    @SerialName("name") val name: String
)