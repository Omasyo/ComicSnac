package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.volume.Volume

internal sealed interface ExtrasUiState<out T>

internal data object InDevelopment : ExtrasUiState<Nothing>

internal data object Loading : ExtrasUiState<Nothing>

internal data class Error(val error: RepositoryResponse.Error) : ExtrasUiState<Nothing>

internal typealias CharacterDetailsUiState = ExtrasUiState<CharacterDetails>

@Immutable
internal data class Success<T>(val content: T) : ExtrasUiState<T>


internal typealias CharactersUiState = ExtrasUiState<List<Character>>

internal typealias MoviesUiState = ExtrasUiState<List<Movie>>

internal typealias TeamsUiState = ExtrasUiState<List<Team>>

internal typealias VolumeUiState = ExtrasUiState<List<Volume>>