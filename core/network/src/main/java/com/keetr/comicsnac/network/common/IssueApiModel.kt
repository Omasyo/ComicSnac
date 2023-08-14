package com.keetr.comicsnac.network.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("issue_number") val issueNumber: String,
    @SerialName("name") val name: String
)