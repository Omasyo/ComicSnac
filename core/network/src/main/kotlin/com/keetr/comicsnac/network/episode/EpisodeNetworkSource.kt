package com.keetr.comicsnac.network.episode

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.episode.models.EpisodeDetailsResponse
import com.keetr.comicsnac.network.episode.models.EpisodeListResponse

interface EpisodeNetworkSource : NetworkSource {
    suspend fun getEpisodeDetails(apiKey: String, id: String): Result<EpisodeDetailsResponse>

    suspend fun getAllEpisodes(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortAirDate: Sort = Sort.Descending
    ): Result<EpisodeListResponse>

    suspend fun getEpisodesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        episodesId: List<Int>,
        sortAirDate: Sort = Sort.Descending
    ): Result<EpisodeListResponse>
}