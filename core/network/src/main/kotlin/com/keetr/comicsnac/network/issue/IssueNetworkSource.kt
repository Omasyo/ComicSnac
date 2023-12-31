package com.keetr.comicsnac.network.issue

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.issue.models.IssueDetailsResponse
import com.keetr.comicsnac.network.issue.models.IssueListResponse

interface IssueNetworkSource : NetworkSource {
    suspend fun getIssueDetails(apiKey: String, id: String): Result<IssueDetailsResponse>

    suspend fun getRecentIssues(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<IssueListResponse>

    suspend fun getAllIssues(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort = Sort.Descending
    ): Result<IssueListResponse>

    suspend fun getIssuesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort = Sort.Descending,
        issuesId: List<Int>
    ): Result<IssueListResponse>
}