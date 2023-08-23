package com.keetr.comicsnac.network.teams

import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.teams.models.TeamDetailsResponse
import com.keetr.comicsnac.network.teams.models.TeamListResponse
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
        makeRequest {
            client.get("teams") {
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
            }
        }
}

private const val DetailsFieldList =
    "aliases,api_detail_url,character_enemies,character_friends,characters,count_of_team_members,deck,description," +
            "first_appeared_in_issue,id,image,movies,name,publisher,site_detail_url,volume_credits"

private const val ListFieldList = "name,deck,site_detail_url,api_detail_url,image"