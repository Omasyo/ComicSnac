package com.keetr.comicsnac.data.issue

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.issue.IssueDetails
import kotlinx.coroutines.flow.Flow

interface IssueRepository {
    fun getIssueDetails(fullId: String): Flow<RepositoryResponse<IssueDetails>>

    fun getRecentIssues(): Flow<RepositoryResponse<List<Issue>>>

    fun getAllIssues(sort: Sort): Flow<PagingData<Issue>>

    fun getIssuesWithId(issuesId: List<Int>, sort: Sort): Flow<PagingData<Issue>>
}