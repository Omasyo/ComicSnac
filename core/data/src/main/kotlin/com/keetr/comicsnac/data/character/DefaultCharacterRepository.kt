package com.keetr.comicsnac.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.network.character.CharacterNetworkSource
import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.search.models.CharacterListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultCharacterRepository @Inject constructor(
    private val networkSource: CharacterNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : CharacterRepository {
    override fun getCharacterDetails(id: String): Flow<RepositoryResponse<CharacterDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getCharacterDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toCharacterDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getRecentCharacters(): Flow<RepositoryResponse<List<Character>>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getRecentCharacters(apiKey, 25, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toCharacters()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllCharacters(genderFilter: Gender): Flow<PagingData<Character>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllCharacters(
                            apiKey,
                            PageSize,
                            PageSize * page,
                            GenderApiModel.valueOf(genderFilter.name)
                        ).getOrThrow().results
                    },
                    mapper = List<CharacterListApiModel>::toCharacters
                )
            }.flow
        }.flowOn(dispatcher)

    override fun getCharactersWithId(charactersId: List<Int>): Flow<PagingData<Character>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        val offset = PageSize * page
                        val pageList = charactersId.subList(
                            charactersId.size.coerceAtMost(offset),
                            charactersId.size.coerceAtMost(offset + PageSize)
                        )
                        if (pageList.isNotEmpty()) {
                            networkSource.getCharactersWithId(
                                apiKey,
                                PageSize,
                                0,
                                pageList
                            ).getOrThrow().results
                        } else emptyList()
                    },
                    mapper = List<CharacterListApiModel>::toCharacters
                )
            }.flow
        }.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}