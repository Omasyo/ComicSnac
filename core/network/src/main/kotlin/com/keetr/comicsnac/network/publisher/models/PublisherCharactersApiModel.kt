package com.keetr.comicsnac.network.publisher.models


import com.keetr.comicsnac.network.common.models.CharacterApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublisherCharactersApiModel(
    @SerialName("characters")
    val characters: List<CharacterApiModel>
)