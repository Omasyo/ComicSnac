package com.keetr.comicsnac.categories.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.movie.MovieRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MovieViewModel @Inject constructor(
    movieRepository: MovieRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Movie>(settingsRepository) {

    override val items =
        movieRepository.getAllMovies().cachedIn(viewModelScope)

}

