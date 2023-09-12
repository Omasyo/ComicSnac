package com.keetr.comicsnac.data.power

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.model.power.PowerDetails
import com.keetr.comicsnac.network.power.PowerNetworkSource
import com.keetr.comicsnac.network.power.models.PowerListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultPowerRepository @Inject constructor(
    private val networkSource: PowerNetworkSource,
    private val authRepository: AuthRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : PowerRepository {
    override fun getPowerDetails(id: String): Flow<RepositoryResponse<PowerDetails>> =
        authRepository.getApiKey().map { apiKey ->
            networkSource.getPowerDetails(apiKey, id)
                .fold(onSuccess = { RepositoryResponse.Success(it.results.toPowerDetails()) }) {
                    fromNetworkError(it)
                }
        }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllPowers(): Flow<PagingData<PowerBasic>> =
        authRepository.getApiKey().flatMapLatest { apiKey ->
            Pager(
                config = pagingConfig,
            ) {
                CustomPagingSource(
                    provider = { page ->
                        networkSource.getAllPowers(
                            apiKey,
                            PageSize,
                            PageSize * page
                        ).getOrThrow().results
                    }, mapper = List<PowerListApiModel>::toPowers
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