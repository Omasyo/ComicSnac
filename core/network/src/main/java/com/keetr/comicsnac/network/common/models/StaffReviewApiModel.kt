package com.keetr.comicsnac.network.common.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StaffReviewApiModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)