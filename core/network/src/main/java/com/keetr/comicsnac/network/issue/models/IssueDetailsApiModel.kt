package com.keetr.comicsnac.network.issue.models


import com.keetr.comicsnac.network.common.models.AssociatedImageApiModel
import com.keetr.comicsnac.network.common.models.CharacterApiModel
import com.keetr.comicsnac.network.common.models.ConceptApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.LocationApiModel
import com.keetr.comicsnac.network.common.models.ObjectApiModel
import com.keetr.comicsnac.network.common.models.PersonCreditApiModel
import com.keetr.comicsnac.network.common.models.StoryArcApiModel
import com.keetr.comicsnac.network.common.models.TeamApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import com.keetr.comicsnac.network.common.serializers.DateShortAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class IssueDetailsApiModel(
    @SerialName("aliases") val aliases: String?,
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("associated_images") val associatedImages: List<AssociatedImageApiModel>,
    @SerialName("character_credits") val characterCredits: List<CharacterApiModel>,
    @SerialName("concept_credits") val conceptCredits: List<ConceptApiModel>,

    @Serializable(DateShortAsStringSerializer::class)
    @SerialName("cover_date") val coverDate: LocalDate?,

    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("issue_number") val issueNumber: String,
    @SerialName("location_credits") val locationCredits: List<LocationApiModel>,
    @SerialName("name") val name: String?,
    @SerialName("object_credits") val objectCredits: List<ObjectApiModel>,
    @SerialName("person_credits") val personCredits: List<PersonCreditApiModel>,
    @SerialName("site_detail_url") val siteDetailUrl: String,

    @Serializable(DateShortAsStringSerializer::class)
    @SerialName("store_date") val storeDate: LocalDate?,

    @SerialName("story_arc_credits") val storyArcCredits: List<StoryArcApiModel>,
    @SerialName("team_credits") val teamCredits: List<TeamApiModel>,
    @SerialName("volume") val volume: VolumeApiModel
)