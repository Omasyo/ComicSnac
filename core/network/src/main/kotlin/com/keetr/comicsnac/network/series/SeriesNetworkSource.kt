package com.keetr.comicsnac.network.series

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.series.models.SeriesDetailsResponse
import com.keetr.comicsnac.network.series.models.SeriesListResponse

interface SeriesNetworkSource : NetworkSource {
    suspend fun getSeriesDetails(apiKey: String, id: String): Result<SeriesDetailsResponse>

    suspend fun getAllSeries(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<SeriesListResponse>

    suspend fun getSeriesWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        seriesId: List<Int>
    ): Result<SeriesListResponse>
}