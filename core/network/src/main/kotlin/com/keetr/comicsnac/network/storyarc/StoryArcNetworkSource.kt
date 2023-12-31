package com.keetr.comicsnac.network.storyarc

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.storyarc.models.StoryArcDetailsResponse
import com.keetr.comicsnac.network.storyarc.models.StoryArcListResponse

interface StoryArcNetworkSource : NetworkSource {
    suspend fun getStoryArcDetails(apiKey: String, id: String): Result<StoryArcDetailsResponse>

    suspend fun getAllStoryArcs(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<StoryArcListResponse>

    suspend fun getStoryArcsWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        storyArcIds: List<Int>
    ): Result<StoryArcListResponse>
}