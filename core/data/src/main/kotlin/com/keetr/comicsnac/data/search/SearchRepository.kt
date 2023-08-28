package com.keetr.comicsnac.data.search

import androidx.paging.PagingData
import com.keetr.comicsnac.model.search.SearchModel
import com.keetr.comicsnac.model.search.SearchType
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchResults(query: String, filter: Set<SearchType>): Flow<PagingData<SearchModel>>
}