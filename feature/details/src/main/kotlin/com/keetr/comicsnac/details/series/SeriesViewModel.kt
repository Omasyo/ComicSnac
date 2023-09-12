package com.keetr.comicsnac.details.series

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.episode.EpisodeRepository
import com.keetr.comicsnac.data.series.SeriesRepository
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.episode.Episode
import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.movie.MovieDetails
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.series.SeriesDetails
import com.keetr.comicsnac.model.team.Team
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
internal class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = checkNotNull(savedStateHandle.get<String>(Arg))

    val detailsUiState =
        seriesRepository.getSeriesDetails(id).map(::getState).stateInCurrentScope()


    val characters: Flow<PagingData<Character>> = getPagingData {
        characterRepository.getCharactersWithId(charactersId)
    }

    val episodes: Flow<PagingData<Episode>> = getPagingData {
        episodeRepository.getEpisodesWithId(episodesId)
    }

    private fun <T> Flow<DetailsUiState<T>>.stateInCurrentScope() =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun <T : Any> getPagingData(init: SeriesDetails.() -> Flow<PagingData<T>>) =
        detailsUiState.flatMapLatest {
            when (it) {
                is Error -> emptyFlow()
                Loading -> emptyFlow()
                is Success -> {
                    init(it.content)
                }
            }
        }.cachedIn(viewModelScope)
}


internal fun <T> getState(response: RepositoryResponse<T>) =
    when (response) {
        is RepositoryResponse.Error -> Error(response)
        is RepositoryResponse.Success -> Success(response.content)
    }
