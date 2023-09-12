package com.keetr.comicsnac.data.episode

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.episode.Episode
import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.episode.EpisodeDetails
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    fun getEpisodeDetails(id: String): Flow<RepositoryResponse<EpisodeDetails>>

    fun getEpisodesWithId(episodesId: List<Int>): Flow<PagingData<Episode>>

    fun getAllEpisodes(): Flow<PagingData<Episode>>
}