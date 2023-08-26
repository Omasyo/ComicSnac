package com.keetr.comicsnac.network.search.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import com.keetr.comicsnac.network.search.models.SearchApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("issue")
data class IssueListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("issue_number") val issueNumber: String,
    @SerialName("name") val name: String?,
    @SerialName("volume") val volume: VolumeApiModel
) : SearchApiModel