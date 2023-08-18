package com.keetr.comicsnac.data.issue

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.model.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.network.common.Sort as NetworkSort
import com.keetr.comicsnac.network.issue.IssueNetworkSource
import com.keetr.comicsnac.network.issue.models.IssueListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultIssueRepository @Inject constructor(
    private val networkSource: IssueNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : IssueRepository {
    override suspend fun getIssueDetails(fullId: String): RepositoryResponse<IssueDetails> =
        withContext(dispatcher) {
            networkSource.getIssueDetails(fullId)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toIssueDetails()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getRecentIssues(): RepositoryResponse<List<Issue>> =
        withContext(dispatcher) {
            networkSource.getRecentIssues(10, 0)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toIssues()) }) {
                    fromNetworkError(it)
                }
        }

    override suspend fun getAllIssues(sort: Sort): Flow<PagingData<Issue>> =
        Pager(
            config = pagingConfig,
        ) {
            CustomPagingSource(
                provider = { page ->
                    networkSource.getAllIssues(
                        PageSize,
                        PageSize * page,
                        NetworkSort.valueOf(sort.name),
                    ).getOrThrow().results
                },
                mapper = List<IssueListApiModel>::toIssues
            )
        }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}