package com.keetr.comicsnac.data.team

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.team.TeamDetails
import com.keetr.comicsnac.network.search.models.TeamListApiModel
import com.keetr.comicsnac.network.team.TeamNetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultTeamRepository @Inject constructor(
    private val networkSource: TeamNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TeamRepository {
    override fun getTeamDetails(id: String): Flow<RepositoryResponse<TeamDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getTeamDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toTeamDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    override fun getAllTeams(): Flow<PagingData<Team>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllTeams(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    },
                    mapper = List<TeamListApiModel>::toTeams
                )
            }.flow
        }.flowOn(dispatcher)

    override fun getTeamsWithId(teamsId: List<Int>): Flow<PagingData<Team>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getTeamsWithId(
                            apiKey,
                            PageSize,
                            PageSize * page,
                            teamsId
                        ).getOrThrow().results
                    },
                    mapper = List<TeamListApiModel>::toTeams
                )
            }.flow
        }.flowOn(dispatcher)

    companion object {
        private const val PageSize = 25

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}