package com.keetr.comicsnac.network.issue.models


import com.keetr.comicsnac.network.character.models.CharacterListApiModel
import com.keetr.comicsnac.network.common.models.AssociatedImageApiModel
import com.keetr.comicsnac.network.common.models.ConceptApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.LocationApiModel
import com.keetr.comicsnac.network.common.models.ObjectApiModel
import com.keetr.comicsnac.network.common.models.PersonCreditApiModel
import com.keetr.comicsnac.network.common.models.StoryArcApiModel
import com.keetr.comicsnac.network.common.models.TeamApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueDetailsApiModel(
    @SerialName("aliases") val aliases: String?,
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("associated_images") val associatedImages: List<AssociatedImageApiModel>,
    @SerialName("character_credits") val characterCredits: List<CharacterListApiModel>,
//    @SerialName("character_died_in") val characterDiedIn: List<CharacterApiModel>,
    @SerialName("concept_credits") val conceptCredits: List<ConceptApiModel>,
    @SerialName("cover_date") val coverDate: String?,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
//    @SerialName("first_appearance_characters") val firstAppearanceCharacters: AnyApiModel?,
//    @SerialName("first_appearance_concepts") val firstAppearanceConcepts: AnyApiModel?,
//    @SerialName("first_appearance_locations") val firstAppearanceLocations: AnyApiModel?,
//    @SerialName("first_appearance_objects") val firstAppearanceObjects: AnyApiModel?,
//    @SerialName("first_appearance_storyarcs") val firstAppearanceStoryarcs: AnyApiModel?,
//    @SerialName("first_appearance_teams") val firstAppearanceTeams: AnyApiModel?,
//    @SerialName("has_staff_review") val hasStaffReview: StaffReviewApiModel,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("issue_number") val issueNumber: String,
    @SerialName("location_credits") val locationCredits: List<LocationApiModel>,
    @SerialName("name") val name: String?,
    @SerialName("object_credits") val objectCredits: List<ObjectApiModel>,
    @SerialName("person_credits") val personCredits: List<PersonCreditApiModel>,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("store_date") val storeDate: String?,
    @SerialName("story_arc_credits") val storyArcCredits: List<StoryArcApiModel>,
    @SerialName("team_credits") val teamCredits: List<TeamApiModel>,
//    @SerialName("team_disbanded_in") val teamDisbandedIn: List<TeamApiModel>,
    @SerialName("volume") val volume: VolumeApiModel
)