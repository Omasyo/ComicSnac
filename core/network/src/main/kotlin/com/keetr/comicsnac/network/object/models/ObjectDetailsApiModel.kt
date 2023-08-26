package com.keetr.comicsnac.network.`object`.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectDetailsApiModel(
    @SerialName("aliases") val aliases: String,
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("count_of_issue_appearances") val countOfIssueAppearances: Int,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("first_appeared_in_issue") val firstAppearedInIssue: IssueApiModel,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)