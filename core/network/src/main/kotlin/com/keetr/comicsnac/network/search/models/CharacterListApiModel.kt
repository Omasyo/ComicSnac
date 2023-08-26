package com.keetr.comicsnac.network.search.models

import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("character")
data class CharacterListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("gender") val gender: GenderApiModel,
    @SerialName("name") val name: String
) : SearchApiModel