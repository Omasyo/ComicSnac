package com.keetr.comicsnac.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.Response
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
import javax.inject.Named

internal class DefaultCharacterRepository @Inject constructor(
    private val networkDataSource: CharacterNetworkSource,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : CharacterRepository {
    override suspend fun getCharacterDetails(apiUrl: String): Response<CharacterDetails> =
        withContext(dispatcher) {
            networkDataSource.getCharacterDetails(apiUrl)
                .fold(onSuccess = { Response.Success(it.results.toCharacterDetail()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getRecentCharacters(): Response<List<Character>> =
        withContext(dispatcher) {
            networkDataSource.getRecentCharacters(100, 0)
                .fold(onSuccess = { Response.Success(it.results.toCharacters()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getAllCharacters(genderFilter: Gender): Flow<PagingData<Character>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkDataSource.getAllCharacters(
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
                    networkDataSource.getCharactersWithId(
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