package com.keetr.comicsnac.network.series

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.series.models.SeriesDetailsResponse
import com.keetr.comicsnac.network.series.models.SeriesListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DefaultSeriesNetworkSource @Inject constructor(
    private val client: HttpClient
) : SeriesNetworkSource {
    override suspend fun getSeriesDetails(id: String): Result<SeriesDetailsResponse> =
        makeRequest {
            client.get("series/4075-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllSeries(pageSize: Int, offset: Int): Result<SeriesListResponse> =
        getSeries(pageSize, offset)

    override suspend fun getSeriesWithId(
        pageSize: Int,
        offset: Int,
        seriesId: List<Int>
    ): Result<SeriesListResponse> = getSeries(pageSize, offset, seriesId = seriesId)

    private suspend fun getSeries(
        pageSize: Int,
        offset: Int,
        seriesId: List<Int> = emptyList()
    ): Result<SeriesListResponse> =
        makeRequest {
            client.get("series_list") {
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
                parameter("sort", "date_last_updated:${Sort.Descending.format}")
                if (seriesId.isNotEmpty()) parameter(
                    "filter", "id:${seriesId.joinToString("|")}"
                )
            }
        }
}

private const val DetailsFieldList =
    "api_detail_url,characters,count_of_episodes,deck,description,episodes,first_episode,id," +
            "image,last_episode,name,publisher,site_detail_url,start_year"

private const val ListFieldList = "api_detail_url,deck,id,image,name,site_detail_url"