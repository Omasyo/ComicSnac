package com.keetr.comicsnac.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.data.movie.MovieRepository
import com.keetr.comicsnac.data.publisher.PublisherRepository
import com.keetr.comicsnac.data.series.SeriesRepository
import com.keetr.comicsnac.data.volume.VolumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    characterRepository: CharacterRepository,
    issueRepository: IssueRepository,
    movieRepository: MovieRepository,
    seriesRepository: SeriesRepository,
    volumeRepository: VolumeRepository,
    publisherRepository: PublisherRepository
) : ViewModel() {

    val charactersUiState =
        characterRepository.getRecentCharacters().map(::getCategoryState).stateInCurrentScope()

    val issueUiState =
        issueRepository.getRecentIssues().map(::getCategoryState).stateInCurrentScope()

    val movieUiState =
        movieRepository.getRecentMovies().map(::getCategoryState).stateInCurrentScope()

    val seriesUiState =
        seriesRepository.getRecentSeries().map(::getCategoryState).stateInCurrentScope()

    val volumeUiState =
        volumeRepository.getRecentVolumes().map(::getCategoryState).stateInCurrentScope()

    val publishersUiState =
        publisherRepository.getPopularPublishers().map(::getCategoryState).stateInCurrentScope()

    private fun <T> getCategoryState(response: RepositoryResponse<List<T>>) =
        when (response) {
            is RepositoryResponse.Error -> Error(response)
            is RepositoryResponse.Success -> Success(response.content)
        }

    private fun <T> Flow<HomeCategoryUiState<T>>.stateInCurrentScope() =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Loading)
}