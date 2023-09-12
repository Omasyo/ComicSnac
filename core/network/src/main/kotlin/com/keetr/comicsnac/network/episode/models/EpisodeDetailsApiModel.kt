package com.keetr.comicsnac.network.episode.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.LocationApiModel
import com.keetr.comicsnac.network.common.models.ObjectApiModel
import com.keetr.comicsnac.network.common.models.SeriesApiModel
import com.keetr.comicsnac.network.common.models.TeamApiModel
import com.keetr.comicsnac.network.common.serializers.DateShortAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EpisodeDetailsApiModel(

    @Serializable(DateShortAsStringSerializer::class)
    @SerialName("air_date") val airDate: LocalDate,

    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("character_credits") val characterCredits: List<CharacterApiModel>,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("episode_number") val episodeNumber: String,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("location_credits") val locationCredits: List<LocationApiModel>,
    @SerialName("name") val name: String,
    @SerialName("object_credits") val objectCredits: List<ObjectApiModel>,
    @SerialName("series") val series: SeriesApiModel,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("team_credits") val teamCredits: List<TeamApiModel>
)