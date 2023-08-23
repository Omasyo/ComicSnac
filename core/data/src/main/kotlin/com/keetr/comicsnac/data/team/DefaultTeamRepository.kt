package com.keetr.comicsnac.data.team

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.character.DefaultCharacterRepository
import com.keetr.comicsnac.data.character.toCharacters
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.team.TeamDetails
import com.keetr.comicsnac.network.character.models.CharacterListApiModel
import com.keetr.comicsnac.network.teams.TeamNetworkSource
import com.keetr.comicsnac.network.teams.models.TeamListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultTeamRepository @Inject constructor(
  private val networkSource: TeamNetworkSource,
  @IODispatcher private val dispatcher: CoroutineDispatcher
) : TeamRepository{
    override fun getTeamDetails(id: String): Flow<RepositoryResponse<TeamDetails>> = flow{
        emit(networkSource.getTeamDetails(id)
            .fold(onSuccess = {RepositoryResponse.Success(it.results.toTeamDetails())}) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getAllTeams(): Flow<PagingData<Team>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getAllTeams(
                        PageSize,
                        PageSize * page
                    ).getOrThrow().results
                },
                mapper = List<TeamListApiModel>::toTeams
            )
        }.flow.flowOn(dispatcher)

    override fun getTeamsWithId(teamsId: List<Int>): Flow<PagingData<Team>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getTeamsWithId(
                        PageSize,
                        PageSize * page,
                        teamsId
                    ).getOrThrow().results
                },
                mapper = List<TeamListApiModel>::toTeams
            )
        }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = DefaultCharacterRepository.PageSize, enablePlaceholders = false
        )
    }
}