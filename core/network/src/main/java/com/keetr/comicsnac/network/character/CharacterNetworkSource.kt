package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.character.models.CharacterDetailsResponse
import com.keetr.comicsnac.network.character.models.CharactersListResponse

enum class Gender(val id: Int) {
    All(-1), Female(2), Male(1), Other(0),
}

enum class Sort(val format: String) {
    Ascending("asc"), Descending("desc"), None("")
}

interface CharacterNetworkSource {
    suspend fun getCharacterDetails(apiUrl: String): CharacterDetailsResponse

    suspend fun getRecentCharacters(pageSize: Int, offset: Int) : CharactersListResponse

    suspend fun getAllCharacters(pageSize: Int, offset: Int, gender: Gender) : CharactersListResponse

    suspend fun getCharactersWithId(pageSize: Int, offset: Int, characterIds: List<Int>): CharactersListResponse
}

/**
 * Filter by gender
 */