package com.keetr.comicsnac.data.concept

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.concept.ConceptDetails
import com.keetr.comicsnac.network.concept.ConceptNetworkSource
import com.keetr.comicsnac.network.search.models.ConceptListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultConceptRepository @Inject constructor(
    private val networkSource: ConceptNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ConceptRepository {
    override fun getConceptDetails(id: String): Flow<RepositoryResponse<ConceptDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getConceptDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toConceptDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllConcepts(): Flow<PagingData<Concept>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllConcepts(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    }, mapper = List<ConceptListApiModel>::toConcepts
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