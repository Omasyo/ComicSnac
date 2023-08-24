package com.keetr.comicsnac.network.person.models


import com.keetr.comicsnac.network.common.models.ImageApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonListApiModel(
    @SerialName("api_detail_url") val apiDetailUrl: String,
    @SerialName("deck") val deck: String?,
    @SerialName("id") val id: Int,
    @SerialName("image") val image: ImageApiModel,
    @SerialName("name") val name: String,
    @SerialName("site_detail_url") val siteDetailUrl: String
)