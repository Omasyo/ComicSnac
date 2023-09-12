package com.keetr.comicsnac.data.storyarc

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.storyarc.StoryArcDetails
import kotlinx.coroutines.flow.Flow

interface StoryArcRepository {
    fun getStoryArcDetails(id: String): Flow<RepositoryResponse<StoryArcDetails>>

    fun getStoryArcsWithId(storyArcsId: List<Int>): Flow<PagingData<StoryArc>>

    fun getAllStoryArcs(): Flow<PagingData<StoryArc>>
}