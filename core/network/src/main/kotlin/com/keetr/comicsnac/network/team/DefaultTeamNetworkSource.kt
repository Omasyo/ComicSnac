package com.keetr.comicsnac.network.team

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.team.models.TeamDetailsResponse
import com.keetr.comicsnac.network.team.models.TeamListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DefaultTeamNetworkSource @Inject constructor(
    private val client: HttpClient
) : TeamNetworkSource {
    override suspend fun getTeamDetails(id: String): Result<TeamDetailsResponse> =
        makeRequest {
            client.get("team/4060-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllTeams(pageSize: Int, offset: Int): Result<TeamListResponse> =
        getTeams(pageSize, offset)

    override suspend fun getTeamsWithId(
        pageSize: Int,
        offset: Int,
        teamIds: List<Int>
    ): Result<TeamListResponse> = getTeams(pageSize, offset, teamsId = teamIds)

    private suspend fun getTeams(
        pageSize: Int,
        offset: Int,
        sort: Sort = Sort.Descending,
        teamsId: List<Int> = emptyList()
    ): Result<TeamListResponse> =
        makeRequest {
            client.get("teams") {
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
                parameter("sort", "date_last_updated:${sort.format}")
                if (teamsId.isNotEmpty()) parameter(
                    "filter", "id:${teamsId.joinToString("|")}"
                )
            }
        }
}

private const val DetailsFieldList =
    "aliases,api_detail_url,character_enemies,character_friends,characters,count_of_team_members,deck,description," +
            "first_appeared_in_issue,id,image,movies,name,publisher,site_detail_url,volume_credits"

private const val ListFieldList = "api_detail_url,deck,id,image,name"