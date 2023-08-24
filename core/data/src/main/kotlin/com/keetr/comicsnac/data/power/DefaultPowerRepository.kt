package com.keetr.comicsnac.data.power

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.keetr.comicsnac.data.CustomPagingSource
import com.keetr.comicsnac.data.di.IODispatcher
import com.keetr.comicsnac.data.fromNetworkError
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.model.power.PowerDetails
import com.keetr.comicsnac.network.power.PowerNetworkSource
import com.keetr.comicsnac.network.power.models.PowerListApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class DefaultPowerRepository @Inject constructor(
    private val networkSource: PowerNetworkSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : PowerRepository {
    override fun getPowerDetails(fullId: String): Flow<RepositoryResponse<PowerDetails>> = flow {
        emit(networkSource.getPowerDetails(fullId)
            .fold(onSuccess = { RepositoryResponse.Success(it.results.toPowerDetails()) }) {
                fromNetworkError(it)
            })
    }.flowOn(dispatcher)

    override fun getAllPowers(sort: Sort): Flow<PagingData<PowerBasic>> = Pager(
        config = pagingConfig,
    ) {
        CustomPagingSource(
            provider = { page ->
                networkSource.getAllPowers(
                    PageSize,
                    PageSize * page
                ).getOrThrow().results
            }, mapper = List<PowerListApiModel>::toPowers
        )
    }.flow.flowOn(dispatcher)

    companion object {
        const val PageSize = 100

        private val pagingConfig = PagingConfig(
            pageSize = PageSize, enablePlaceholders = false
        )
    }
}