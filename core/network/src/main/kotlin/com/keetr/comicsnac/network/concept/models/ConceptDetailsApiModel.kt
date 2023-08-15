package com.keetr.comicsnac.network.concept.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConceptDetailsApiModel(
    @SerialName("aliases") val aliases: String?,
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("first_appeared_in_issue") val firstAppearedInIssue: IssueApiModel,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("start_year") val startYear: String,
    @SerialName("volume_credits") val volumeCredits: List<VolumeApiModel>,
)