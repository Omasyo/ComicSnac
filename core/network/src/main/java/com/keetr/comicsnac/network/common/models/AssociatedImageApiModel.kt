package com.keetr.comicsnac.network.common.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssociatedImageApiModel(
    @SerialName("caption") val caption: String?,
    @SerialName("id") val id: Int,
    @SerialName("image_tags") val imageTags: String,
    @SerialName("original_url") val originalUrl: String
)