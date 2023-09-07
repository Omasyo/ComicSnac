package com.keetr.comicsnac.details

import androidx.compose.runtime.Immutable
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.model.`object`.ObjectDetails
import com.keetr.comicsnac.model.person.PersonDetails
import com.keetr.comicsnac.model.power.PowerDetails
import com.keetr.comicsnac.model.publisher.PublisherDetails
import com.keetr.comicsnac.model.team.TeamDetails
import com.keetr.comicsnac.model.volume.VolumeDetails


internal sealed interface DetailsUiState<out T>

internal data object Loading : DetailsUiState<Nothing>

internal data class Error(val error: RepositoryResponse.Error) : DetailsUiState<Nothing>

@Immutable
internal data class Success<T>(val content: T) : DetailsUiState<T>

internal typealias CharacterDetailsUiState = DetailsUiState<CharacterDetails>

internal typealias IssueDetailsUiState = DetailsUiState<IssueDetails>

internal typealias ObjectDetailsUiState = DetailsUiState<ObjectDetails>

internal typealias PersonDetailsUiState = DetailsUiState<PersonDetails>

internal typealias PowerDetailsUiState = DetailsUiState<PowerDetails>

internal typealias PublisherDetailsUiState = DetailsUiState<PublisherDetails>

internal typealias TeamDetailsUiState = DetailsUiState<TeamDetails>

internal typealias VolumeDetailsUiState = DetailsUiState<VolumeDetails>