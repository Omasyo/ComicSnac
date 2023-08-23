package com.keetr.comicsnac.network.team.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.common.models.MovieApiModel
import com.keetr.comicsnac.network.common.models.PublisherApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDetailsApiModel(
    @SerialName("aliases") val aliases: String?,
    @SerialName("character_enemies") val characterEnemies: List<CharacterApiModel>,
    @SerialName("character_friends") val characterFriends: List<CharacterApiModel>,
    @SerialName("characters") val characters: List<CharacterApiModel>,
    @SerialName("count_of_team_members") val countOfTeamMembers: Int,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("first_appeared_in_issue") val firstAppearedInIssue: IssueApiModel,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("movies") val movies: List<MovieApiModel>,
    @SerialName("name") val name: String,
    @SerialName("publisher") val publisher: PublisherApiModel?,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("volume_credits") val volumeCredits: List<VolumeApiModel>
)