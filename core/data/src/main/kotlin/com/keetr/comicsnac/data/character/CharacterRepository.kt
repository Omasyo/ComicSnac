package com.keetr.comicsnac.data.character

import androidx.paging.PagingData
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.other.Gender
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacterDetails(fullId: String): Flow<RepositoryResponse<CharacterDetails>>

    fun getRecentCharacters(): Flow<RepositoryResponse<List<Character>>>

    fun getAllCharacters(genderFilter: Gender): Flow<PagingData<Character>>

    fun getCharactersWithId(charactersId: List<Int>): Flow<PagingData<Character>>
}