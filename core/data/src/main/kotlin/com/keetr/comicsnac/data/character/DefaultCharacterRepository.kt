package com.keetr.comicsnac.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.network.character.CharacterNetworkSource
import com.keetr.comicsnac.network.character.models.CharacterListApiModel
import com.keetr.comicsnac.network.common.models.GenderApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCharacterRepository @Inject constructor(
    private val networkSource: CharacterNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : CharacterRepository {
    override suspend fun getCharacterDetails(apiUrl: String): RepositoryResponse<CharacterDetails> =
        withContext(dispatcher) {
            networkSource.getCharacterDetails(apiUrl)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toCharacterDetail()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getRecentCharacters(): RepositoryResponse<List<Character>> =
        withContext(dispatcher) {
            networkSource.getRecentCharacters(25, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toCharacters()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getAllCharacters(genderFilter: Gender): Flow<PagingData<Character>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getAllCharacters(
                        PageSize,
                        PageSize * page,
                        GenderApiModel.valueOf(genderFilter.name)
                    ).getOrThrow().results
                },
                mapper = List<CharacterListApiModel>::toCharacters
            )
        }.flow.flowOn(dispatcher)

    override suspend fun getCharactersWithId(charactersId: List<Int>): Flow<PagingData<Character>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getCharactersWithId(
                        PageSize,
                        PageSize * page,
                        charactersId
                    ).getOrThrow().results
                },
                mapper = List<CharacterListApiModel>::toCharacters
            )
        }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}