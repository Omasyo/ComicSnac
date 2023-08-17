package com.keetr.comicsnac.data.issue

import androidx.paging.PagingData
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.issue.IssueDetails
import kotlinx.coroutines.flow.Flow

interface IssueRepository {
    suspend fun getIssueDetails(apiUrl: String): RepositoryResponse<IssueDetails>

    suspend fun getRecentIssues() : RepositoryResponse<List<Issue>>

    suspend fun getAllIssues(sort: Sort): Flow<PagingData<Character>>
}