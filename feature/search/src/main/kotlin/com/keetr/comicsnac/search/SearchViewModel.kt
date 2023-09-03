package com.keetr.comicsnac.search

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.search.SearchRepository
import com.keetr.comicsnac.model.search.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var query by mutableStateOf("")
        private set


    fun onQueryChanged(newValue: String) {
        query = newValue
    }

    fun clearQuery() {
        query = ""
    }

    private val _submittedQuery = MutableStateFlow("")
    val submittedQuery = _submittedQuery.asStateFlow()
    fun onSearch() {
        Log.d("TAG", "SearchScreen: $query Search clicked")
        _submittedQuery.value = query
    }

    private val filtersFlow = MutableStateFlow(SearchType.entries.toSet())
    val filters @Composable get() = filtersFlow.collectAsState().value

    fun toggleFilter(type: SearchType) {
        filtersFlow.value =
            (if (filtersFlow.value.contains(type)) filtersFlow.value - type else filtersFlow.value + type)
    }

    fun selectFilter(type: SearchType) {
        filtersFlow.value = setOf(type)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults = _submittedQuery.flatMapLatest { searchQuery ->
        if (searchQuery.isEmpty()) emptyFlow()
        else
            filtersFlow.flatMapLatest { filter ->
                searchRepository.getSearchResults(searchQuery, filter)
            }
    }.cachedIn(viewModelScope)
}