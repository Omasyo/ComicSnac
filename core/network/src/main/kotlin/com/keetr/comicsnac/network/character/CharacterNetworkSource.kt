package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.character.models.CharacterDetailsResponse
import com.keetr.comicsnac.network.character.models.CharactersListResponse
import com.keetr.comicsnac.network.common.models.GenderApiModel

interface CharacterNetworkSource : NetworkSource {

    suspend fun getCharacterDetails(apiKey: String, id: String): Result<CharacterDetailsResponse>

    suspend fun getRecentCharacters(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<CharactersListResponse>

    suspend fun getAllCharacters(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        gender: GenderApiModel
    ): Result<CharactersListResponse>

    suspend fun getCharactersWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        characterIds: List<Int>
    ): Result<CharactersListResponse>
}