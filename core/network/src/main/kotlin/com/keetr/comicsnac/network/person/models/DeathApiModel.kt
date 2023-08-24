package com.keetr.comicsnac.network.person.models


import com.keetr.comicsnac.network.common.serializers.DateAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class DeathApiModel(

    @Serializable(DateAsStringSerializer::class)
    @SerialName("date") val date: LocalDateTime,

    @SerialName("timezone") val timezone: String,
    @SerialName("timezone_type") val timezoneType: Int
)