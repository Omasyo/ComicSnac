package com.keetr.comicsnac.network.team

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.team.models.TeamDetailsResponse
import com.keetr.comicsnac.network.team.models.TeamListResponse

interface TeamNetworkSource: NetworkSource {
    suspend fun getTeamDetails(id: String): Result<TeamDetailsResponse>

    suspend fun getAllTeams(
        pageSize: Int,
        offset: Int
    ): Result<TeamListResponse>

    suspend fun getTeamsWithId(
        pageSize: Int,
        offset: Int,
        teamIds: List<Int>
    ): Result<TeamListResponse>
}