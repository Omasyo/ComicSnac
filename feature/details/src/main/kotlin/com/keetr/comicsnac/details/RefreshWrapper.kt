package com.keetr.comicsnac.details

import com.keetr.comicsnac.data.RepositoryResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class RefreshWrapper<T>(
    private val scope: CoroutineScope,
    private val request: () -> Flow<RepositoryResponse<T>>
) {
    private val mutableFlow = MutableStateFlow<DetailsUiState<T>>(Loading)
    val response = mutableFlow.stateIn(scope, SharingStarted.WhileSubscribed(), Loading)

    init {
        refresh()
    }

    fun refresh() {
        scope.launch {
            mutableFlow.value = Loading
            mutableFlow.value = when (val response = request().first()) {
                is RepositoryResponse.Error -> Error(response, ::refresh)
                is RepositoryResponse.Success -> Success(response.content, ::refresh)
            }

        }
    }
}