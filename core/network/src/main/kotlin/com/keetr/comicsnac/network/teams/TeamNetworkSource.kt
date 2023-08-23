package com.keetr.comicsnac.network.teams

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.teams.models.TeamDetailsResponse
import com.keetr.comicsnac.network.teams.models.TeamListResponse

interface TeamNetworkSource: NetworkSource {
    suspend fun getTeamDetails(id: String): Result<TeamDetailsResponse>

    suspend fun getAllTeams(
        pageSize: Int,
        offset: Int
    ): Result<TeamListResponse>
}