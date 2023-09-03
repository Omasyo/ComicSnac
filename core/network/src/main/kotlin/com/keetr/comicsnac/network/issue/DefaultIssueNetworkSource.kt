package com.keetr.comicsnac.network.issue

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.issue.models.IssueDetailsResponse
import com.keetr.comicsnac.network.issue.models.IssueListResponse
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate
import javax.inject.Inject

internal class DefaultIssueNetworkSource @Inject constructor(
    private val client: HttpClient
) : IssueNetworkSource {
    override suspend fun getIssueDetails(apiKey: String, id: String): Result<IssueDetailsResponse> =
        makeRequest {
            client.get("issue/4000-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getRecentIssues(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<IssueListResponse> =
        getIssues(apiKey, pageSize, offset, Sort.Descending)

    override suspend fun getAllIssues(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort
    ): Result<IssueListResponse> =
        getIssues(apiKey, pageSize, offset, sortCoverDate)

    override suspend fun getIssuesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort,
        issuesId: List<Int>
    ): Result<IssueListResponse> = getIssues(apiKey, pageSize, offset, sortCoverDate, issuesId)

    private suspend fun getIssues(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort = Sort.None,
        issuesId: List<Int> = emptyList()
    ): Result<IssueListResponse> = makeRequest {
        client.get("issues") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (sortCoverDate != Sort.None) parameter(
                "sort", "cover_date:${sortCoverDate.format}"
            )

            var filter = "cover_date:${LocalDate.now().minusDays(14)}|${LocalDate.now()}"

            if (issuesId.isNotEmpty()) filter += "id:${issuesId.joinToString("|")}"

            parameter("filter", filter)
        }
    }
}


private const val DetailsFieldList = "aliases,api_detail_url,associated_images,character_credits," +
        "concept_credits,cover_date,deck,description,id,image,issue_number,location_credits,name," +
        "object_credits,person_credits,site_detail_url,store_date,story_arc_credits,team_credits," +
        "volume"

private const val ListFieldList =
    "api_detail_url,deck,id,image,issue_number,name,volume"