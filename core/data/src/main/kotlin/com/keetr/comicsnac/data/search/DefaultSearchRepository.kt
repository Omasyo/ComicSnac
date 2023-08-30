package com.keetr.comicsnac.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.model.search.SearchModel
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.network.search.SearchNetworkSource
import com.keetr.comicsnac.network.search.models.SearchApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultSearchRepository @Inject constructor(
    private val networkSource: SearchNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SearchRepository {
    override fun getSearchResults(
        query: String,
        filter: Set<SearchType>
    ): Flow<PagingData<SearchModel>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getSearchResults(
                        query,
                        filter.joinToString(",") { it.format },
                        PageSize,
                        PageSize * page
                    ).getOrThrow().results
                },
                mapper = List<SearchApiModel>::toSearchModels
            )
        }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 10

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}