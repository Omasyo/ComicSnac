package com.keetr.comicsnac.data.team

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.team.TeamDetails
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    fun getTeamDetails(id: String): Flow<RepositoryResponse<TeamDetails>>

    fun getAllTeams(): Flow<PagingData<Team>>

    fun getTeamsWithId(teamsId: List<Int>): Flow<PagingData<Team>>
}