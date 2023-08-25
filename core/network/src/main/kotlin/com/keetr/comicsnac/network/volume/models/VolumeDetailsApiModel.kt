package com.keetr.comicsnac.network.volume.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.common.models.PublisherApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeDetailsApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("count_of_issues") val countOfIssues: Int,
    @SerialName("deck") val deck: String?,
    @SerialName("description") val description: String?,
    @SerialName("first_issue") val firstIssue: IssueApiModel?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("issues") val issues: List<IssueApiModel>,
    @SerialName("last_issue") val lastIssue: IssueApiModel?,
    @SerialName("name") val name: String,
    @SerialName("publisher") val publisher: PublisherApiModel?,
    @SerialName("site_detail_url") val siteDetailUrl: String,
    @SerialName("start_year") val startYear: String?
)