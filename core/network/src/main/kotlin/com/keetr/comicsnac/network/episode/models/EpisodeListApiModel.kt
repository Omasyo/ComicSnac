package com.keetr.comicsnac.network.episode.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.SeriesApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String,
    @SerialName("series") val series: SeriesApiModel
)