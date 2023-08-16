package com.keetr.comicsnac.data.character

import androidx.paging.PagingData
import com.keetr.comicsnac.model.Response
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.other.Gender
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterDetails(apiUrl: String): Response<CharacterDetails>

    suspend fun getRecentCharacters(): Response<List<Character>>

    suspend fun getAllCharacters(genderFilter: Gender): Flow<PagingData<Character>>

    suspend fun getCharactersWithId(charactersId: List<Int>): Flow<PagingData<Character>>
}