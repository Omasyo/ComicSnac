package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.character.models.CharacterDetailsResponse
import com.keetr.comicsnac.network.character.models.CharactersListResponse
import com.keetr.comicsnac.network.common.models.GenderApiModel

interface CharacterNetworkSource {
    suspend fun getCharacterDetails(apiUrl: String): CharacterDetailsResponse

    suspend fun getRecentCharacters(pageSize: Int, offset: Int) : CharactersListResponse

    suspend fun getAllCharacters(pageSize: Int, offset: Int, gender: GenderApiModel) : CharactersListResponse

    suspend fun getCharactersWithId(pageSize: Int, offset: Int, characterIds: List<Int>): CharactersListResponse
}

/**
 * Filter by gender
 */