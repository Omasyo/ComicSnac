package com.keetr.comicsnac.network.movie.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.LocationApiModel
import com.keetr.comicsnac.network.common.models.ObjectApiModel
import com.keetr.comicsnac.network.common.models.PersonApiModel
import com.keetr.comicsnac.network.common.models.StudioApiModel
import com.keetr.comicsnac.network.common.models.TeamApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("box_office_revenue") val boxOfficeRevenue: String?,
    @SerialName("budget") val budget: String?,
    @SerialName("characters") val characters: List<CharacterApiModel>,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("locations") val locations: List<LocationApiModel>,
    @SerialName("name") val name: String,
    @SerialName("objects") val objects: List<ObjectApiModel>,
    @SerialName("producers") val producers: List<PersonApiModel>?,
    @SerialName("rating") val rating: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("runtime") val runtime: String,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("studios") val studios: List<StudioApiModel>?,
    @SerialName("teams") val teams: List<TeamApiModel>,
    @SerialName("total_revenue") val totalRevenue: String?,
    @SerialName("writers") val writers: List<PersonApiModel>?
)