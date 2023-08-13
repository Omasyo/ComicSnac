package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.character.models.CharactersDetailsResponseApiModel
import com.keetr.comicsnac.network.character.models.ResultsApiModel

enum class Gender(id: Int) {
    All(-1), Female(2), Male(1), Other(0),
}

interface CharacterNetworkSource {
    suspend fun getCharacterDetails(apiUrl: String): CharactersDetailsResponseApiModel<ResultsApiModel>

    suspend fun getCharacters(pageSize: Int, offset: Int, gender: Gender)

    suspend fun getCharactersWithId(pageSize: Int, offset: Int, characterIds: List<Int>)
}

/**
 * Filter by gender
 */