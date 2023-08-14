package com.keetr.comicsnac.network.common.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageApiModel(
    @SerialName("icon_url") val iconUrl: String,
    @SerialName("image_tags") val imageTags: String,
    @SerialName("medium_url") val mediumUrl: String,
    @SerialName("original_url") val originalUrl: String,
    @SerialName("screen_large_url") val screenLargeUrl: String,
    @SerialName("screen_url") val screenUrl: String,
    @SerialName("small_url") val smallUrl: String,
    @SerialName("super_url") val superUrl: String,
    @SerialName("thumb_url") val thumbUrl: String,
    @SerialName("tiny_url") val tinyUrl: String
)