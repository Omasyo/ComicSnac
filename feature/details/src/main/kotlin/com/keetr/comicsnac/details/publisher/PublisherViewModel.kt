package com.keetr.comicsnac.details.publisher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.publisher.PublisherRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.character.getState
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.volume.Volume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PublisherViewModel @Inject constructor(
    private val publisherRepository: PublisherRepository,
    private val characterRepository: CharacterRepository,
    private val volumeRepository: VolumeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        publisherRepository.getPublisherDetails(id).map(::getState).stateInCurrentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    val characters: Flow<PagingData<Character>> =
        publisherRepository.getPublisherCharactersId(id).map(::getState).flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    characterRepository.getCharactersWithId(it.content)
                }
            }
        }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val volumes: Flow<PagingData<Volume>> =
        publisherRepository.getPublisherVolumesId(id).map(::getState).flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    volumeRepository.getVolumesWithId(it.content)
                }
            }
        }.cachedIn(viewModelScope)

    private fun <T> Flow<DetailsUiState<T>>.stateInCurrentScope() =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}

private fun <T> getState(response: RepositoryResponse<T>) =
    when (response) {
        is RepositoryResponse.Error -> Error(response)
        is RepositoryResponse.Success -> Success(response.content)
    }