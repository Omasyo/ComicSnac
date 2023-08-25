package com.keetr.comicsnac.network.series.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import com.keetr.comicsnac.network.common.models.EpisodeApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.PublisherApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailsApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("characters") val characters: List<CharacterApiModel> = emptyList(),
    @SerialName("count_of_episodes") val countOfEpisodes: Int,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("episodes") val episodes: List<EpisodeApiModel> = emptyList(),
    @SerialName("first_episode") val firstEpisode: EpisodeApiModel?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("last_episode") val lastEpisode: EpisodeApiModel?,
    @SerialName("name") val name: String,
    @SerialName("publisher") val publisher: PublisherApiModel?,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("start_year") val startYear: String?
)