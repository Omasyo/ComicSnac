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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
        RefreshWrapper(viewModelScope) { characterRepository.getRecentCharacters() }.response

    val issueUiState =
        RefreshWrapper(viewModelScope) { issueRepository.getRecentIssues() }.response

    val movieUiState =
        RefreshWrapper(viewModelScope) { movieRepository.getRecentMovies() }.response

    val seriesUiState =
        RefreshWrapper(viewModelScope) { seriesRepository.getRecentSeries() }.response

    val volumeUiState =
        RefreshWrapper(viewModelScope) { volumeRepository.getRecentVolumes() }.response

    val publishersUiState =
        RefreshWrapper(viewModelScope) { publisherRepository.getPopularPublishers() }.response

    private class RefreshWrapper<T>(
        private val scope: CoroutineScope,
        private val request: () -> Flow<RepositoryResponse<List<T>>>
    ) {
        private val mutableFlow = MutableStateFlow<HomeCategoryUiState<T>>(Loading)
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
}