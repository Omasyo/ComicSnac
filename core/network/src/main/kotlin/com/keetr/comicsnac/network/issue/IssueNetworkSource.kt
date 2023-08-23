package com.keetr.comicsnac.network.issue

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.issue.models.IssueDetailsResponse
import com.keetr.comicsnac.network.issue.models.IssueListResponse

interface IssueNetworkSource : NetworkSource {
    suspend fun getIssueDetails(id: String): Result<IssueDetailsResponse>

    suspend fun getRecentIssues(pageSize: Int, offset: Int): Result<IssueListResponse>

    suspend fun getAllIssues(
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort = Sort.None
    ): Result<IssueListResponse>
}