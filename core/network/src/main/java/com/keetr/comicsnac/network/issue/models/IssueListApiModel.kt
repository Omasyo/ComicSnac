package com.keetr.comicsnac.network.issue.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.serializers.DateShortAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class IssueListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,

    @Serializable(DateShortAsStringSerializer::class)
    @SerialName("cover_date") val coverDate: LocalDate?,

    @SerialName("deck") val deck: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String?,
    @SerialName("site_detail_url") val siteDetailUrl: String,
)