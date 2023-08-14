package com.keetr.comicsnac.network.concept.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConceptApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String,
)