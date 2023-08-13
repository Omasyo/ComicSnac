package com.keetr.comicsnac.network.character.models


import com.keetr.comicsnac.network.basic.CharacterApiModel
import com.keetr.comicsnac.network.basic.CreatorApiModel
import com.keetr.comicsnac.network.basic.ImageApiModel
import com.keetr.comicsnac.network.basic.IssueApiModel
import com.keetr.comicsnac.network.basic.MovieApiModel
import com.keetr.comicsnac.network.basic.OriginApiModel
import com.keetr.comicsnac.network.basic.PowerApiModel
import com.keetr.comicsnac.network.basic.PublisherApiModel
import com.keetr.comicsnac.network.basic.TeamApiModel
import com.keetr.comicsnac.network.basic.VolumeApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultsApiModel(
    @SerialName("aliases") val aliases: String?,
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("character_enemies") val characterEnemies: List<CharacterApiModel>,
    @SerialName("character_friends") val characterFriends: List<CharacterApiModel>,
    @SerialName("count_of_issue_appearances") val countOfIssueAppearances: Int,
    @SerialName("creators") val creators: List<CreatorApiModel>,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("first_appeared_in_issue") val firstAppearedInIssue: IssueApiModel,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("movies") val movies: List<MovieApiModel>,
    @SerialName("name") val name: String,
    @SerialName("origin") val origin: OriginApiModel?,
    @SerialName("powers") val powers: List<PowerApiModel>,
    @SerialName("publisher") val publisher: PublisherApiModel?,
    @SerialName("real_name") val realName: String?,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("team_enemies") val teamEnemies: List<TeamApiModel>,
    @SerialName("team_friends") val teamFriends: List<TeamApiModel>,
    @SerialName("teams") val teams: List<TeamApiModel>,
    @SerialName("volume_credits") val volumeCredits: List<VolumeApiModel>
)