package com.keetr.comicsnac.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.search.SearchModel
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.network.search.SearchNetworkSource
import com.keetr.comicsnac.network.search.models.SearchApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultSearchRepository @Inject constructor(
    private val networkSource: SearchNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SearchRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSearchResults(
        query: String,
        filter: Set<SearchType>
    ): Flow<PagingData<SearchModel>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        if (page < 1) {
                            networkSource.getSearchResults(
                                apiKey,
                                query,
                                filter.joinToString(",") { it.format },
                                PageSize,
                                PageSize * page
                            ).getOrThrow().results
                        } else emptyList() //TODO: Bug in comicvine search api. Workaround to get just the first page
                    },
                    mapper = List<SearchApiModel>::toSearchModels
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