package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.volume.Volume

internal typealias ExtrasUiState<T> = DetailsUiState<List<T>>

internal typealias CharactersUiState = ExtrasUiState<Character>

internal typealias MoviesUiState = ExtrasUiState<Movie>

internal typealias TeamsUiState = ExtrasUiState<Team>

internal typealias VolumeUiState = ExtrasUiState<Volume>