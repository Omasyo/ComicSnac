package com.keetr.comicsnac.network.publisher.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.StoryArcApiModel
import com.keetr.comicsnac.network.common.models.TeamApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublisherDetailsApiModel(
    @SerialName("aliases") val aliases: String,
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("location_address") val locationAddress: String?,
    @SerialName("location_city") val locationCity: String?,
    @SerialName("location_state") val locationState: String?,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("teams") val teams: List<TeamApiModel>
)