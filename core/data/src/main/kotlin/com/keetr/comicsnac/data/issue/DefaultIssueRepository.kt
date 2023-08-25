package com.keetr.comicsnac.data.issue

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.network.common.Sort as NetworkSort
import com.keetr.comicsnac.network.issue.IssueNetworkSource
import com.keetr.comicsnac.network.issue.models.IssueListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultIssueRepository @Inject constructor(
    private val networkSource: IssueNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : IssueRepository {
    override fun getIssueDetails(fullId: String): Flow<RepositoryResponse<IssueDetails>> = flow {
        emit(networkSource.getIssueDetails(fullId)
            .fold(onSuccess = { RepositoryResponse.Success(it.results.toIssueDetails()) }) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getRecentIssues(): Flow<RepositoryResponse<List<Issue>>> = flow {
        emit(networkSource.getRecentIssues(10, 0)
            .fold(onSuccess = { RepositoryResponse.Success(it.results.toIssues()) }) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getAllIssues(sort: Sort): Flow<PagingData<Issue>> = Pager(
        config = pagingConfig,
    ) {
        CustomPagingSource(
            provider = { page ->
                networkSource.getAllIssues(
                    PageSize,
                    PageSize * page,
                    NetworkSort.valueOf(sort.name),
                ).getOrThrow().results
            }, mapper = List<IssueListApiModel>::toIssues
        )
    }.flow.flowOn(dispatcher)

    override fun getIssuesWithId(issuesId: List<Int>, sort: Sort): Flow<PagingData<Issue>> = Pager(
        config = pagingConfig,
    ) {
        CustomPagingSource(
            provider = { page ->
                networkSource.getIssuesWithId(
                    PageSize,
                    PageSize * page,
                    NetworkSort.valueOf(sort.name),
                    issuesId
                ).getOrThrow().results
            }, mapper = List<IssueListApiModel>::toIssues
        )
    }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}