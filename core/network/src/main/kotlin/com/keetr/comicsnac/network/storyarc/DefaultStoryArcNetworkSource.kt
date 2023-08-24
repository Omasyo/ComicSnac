package com.keetr.comicsnac.network.storyarc

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.storyarc.models.StoryArcDetailsResponse
import com.keetr.comicsnac.network.storyarc.models.StoryArcListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DefaultStoryArcNetworkSource @Inject constructor(
    private val client: HttpClient
) : StoryArcNetworkSource {
    override suspend fun getStoryArcDetails(id: String): Result<StoryArcDetailsResponse> =
        makeRequest {
            client.get("story_arc/4045-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllStoryArcs(pageSize: Int, offset: Int): Result<StoryArcListResponse> =
        getStoryArcs(pageSize, offset)

    override suspend fun getStoryArcsWithId(
        pageSize: Int,
        offset: Int,
        storyArcIds: List<Int>
    ): Result<StoryArcListResponse> = getStoryArcs(pageSize, offset, storyArcIds = storyArcIds)

    private suspend fun getStoryArcs(
        pageSize: Int,
        offset: Int,
        sort: Sort = Sort.Descending,
        storyArcIds: List<Int> = emptyList()
    ): Result<StoryArcListResponse> =
        makeRequest {
            client.get("story_arcs") {
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
                parameter("sort", "date_last_updated:${sort.format}")
                if (storyArcIds.isNotEmpty()) parameter(
                    "filter", "id:${storyArcIds.joinToString("|")}"
                )
            }
        }
}

private const val DetailsFieldList =
    "api_detail_url,deck,description,episode,first_appeared_in_episode,first_appeared_in_issue," +
            "id,image,issues,name,publisher,site_detail_url"

private const val ListFieldList = "api_detail_url,deck,id,image,name,site_detail_url"