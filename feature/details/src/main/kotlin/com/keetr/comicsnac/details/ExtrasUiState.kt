package com.keetr.comicsnac.details

import android.health.connect.datatypes.units.Volume
import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.team.Team

sealed interface ExtrasUiState<out T>

data object InDevelopment : ExtrasUiState<Nothing>

data object Loading : ExtrasUiState<Nothing>

data class Error(val error: RepositoryResponse.Error) : ExtrasUiState<Nothing>

typealias CharacterDetailsUiState = ExtrasUiState<CharacterDetails>

@Immutable
data class Success<T>(val content: T) : ExtrasUiState<T>


typealias CharactersUiState = ExtrasUiState<List<Character>>

typealias MoviesUiState = ExtrasUiState<List<Movie>>

typealias TeamsUiState = ExtrasUiState<List<Team>>

typealias VolumeUiState = ExtrasUiState<Volume>