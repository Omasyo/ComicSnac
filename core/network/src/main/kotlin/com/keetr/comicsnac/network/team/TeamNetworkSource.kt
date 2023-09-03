package com.keetr.comicsnac.network.team

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.team.models.TeamDetailsResponse
import com.keetr.comicsnac.network.team.models.TeamListResponse

interface TeamNetworkSource : NetworkSource {
    suspend fun getTeamDetails(apiKey: String, id: String): Result<TeamDetailsResponse>

    suspend fun getAllTeams(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<TeamListResponse>

    suspend fun getTeamsWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        teamIds: List<Int>
    ): Result<TeamListResponse>
}