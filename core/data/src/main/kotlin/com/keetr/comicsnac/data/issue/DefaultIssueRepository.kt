package com.keetr.comicsnac.data.issue

import androidx.paging.PagingData
import com.keetr.comicsnac.data.character.toCharacterDetail
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.network.issue.IssueNetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultIssueRepository @Inject constructor(
    private val networkSource: IssueNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : IssueRepository {
    override suspend fun getIssueDetails(apiUrl: String): RepositoryResponse<IssueDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentIssues(): RepositoryResponse<List<Issue>> =
        withContext(dispatcher) {
            networkSource.getRecentIssues(10, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toIssues()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getAllIssues(sort: Sort): Flow<PagingData<Character>> {
        TODO("Not yet implemented")
    }
}