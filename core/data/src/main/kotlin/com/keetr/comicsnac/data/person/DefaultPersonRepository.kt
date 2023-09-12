package com.keetr.comicsnac.data.person

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.person.Person
import com.keetr.comicsnac.model.person.PersonDetails
import com.keetr.comicsnac.network.person.PersonNetworkSource
import com.keetr.comicsnac.network.search.models.PersonListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultPersonRepository @Inject constructor(
    private val networkSource: PersonNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : PersonRepository {
    override fun getPersonDetails(id: String): Flow<RepositoryResponse<PersonDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getPersonDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toPersonDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllPeople(sort: Sort): Flow<PagingData<Person>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllPeople(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    }, mapper = List<PersonListApiModel>::toPeople
                )
            }.flow
        }.flowOn(dispatcher)

    companion object {
        private const val PageSize = 25

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}