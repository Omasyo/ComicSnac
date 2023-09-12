package com.keetr.comicsnac.network.episode

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.episode.models.EpisodeDetailsResponse
import com.keetr.comicsnac.network.episode.models.EpisodeListResponse
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate
import javax.inject.Inject

internal class DefaultEpisodeNetworkSource @Inject constructor(
    private val client: HttpClient
) : EpisodeNetworkSource {
    override suspend fun getEpisodeDetails(
        apiKey: String,
        id: String
    ): Result<EpisodeDetailsResponse> =
        makeRequest {
            client.get("episode/4070-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllEpisodes(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortAirDate: Sort
    ): Result<EpisodeListResponse> =
        getEpisodes(apiKey, pageSize, offset, sortAirDate)

    override suspend fun getEpisodesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        episodesId: List<Int>,
        sortAirDate: Sort
    ): Result<EpisodeListResponse> = getEpisodes(apiKey, pageSize, offset, sortAirDate, episodesId)

    private suspend fun getEpisodes(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort = Sort.None,
        episodesId: List<Int> = emptyList()
    ): Result<EpisodeListResponse> = makeRequest {
        client.get("episodes") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (sortCoverDate != Sort.None) parameter(
                "sort", "air_date:${sortCoverDate.format}"
            )

            parameter("filter", "id:${episodesId.joinToString("|")}")
        }
    }
}


private const val DetailsFieldList = "air_date,api_detail_url,character_credits,deck,description," +
        "episode_number,id,image,location_credits,name,object_credits,series,site_detail_url," +
        "team_credits"

private const val ListFieldList =
    "api_detail_url,deck,id,image,name"