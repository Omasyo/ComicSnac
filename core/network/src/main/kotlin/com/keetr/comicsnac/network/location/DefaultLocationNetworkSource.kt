package com.keetr.comicsnac.network.location

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.location.models.LocationDetailsResponse
import com.keetr.comicsnac.network.location.models.LocationListResponse
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultLocationNetworkSource @Inject constructor(
    private val client: HttpClient
) : LocationNetworkSource {
    override suspend fun getLocationDetails(id: String): Result<LocationDetailsResponse> =
        makeRequest {
            client.get("location/4020-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllLocations(
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort
    ): Result<LocationListResponse> =
        getLocations(pageSize, offset, sortCoverDate)

    override suspend fun getLocationsWithId(
        pageSize: Int,
        offset: Int,
        sort: Sort,
        locationsId: List<Int>
    ): Result<LocationListResponse> = getLocations(pageSize, offset, sort, locationsId)

    private suspend fun getLocations(
        pageSize: Int,
        offset: Int,
        sort: Sort = Sort.None,
        locationsId: List<Int> = emptyList()
    ): Result<LocationListResponse> = makeRequest {
        client.get("locations") {
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (sort != Sort.None) parameter(
                "sort", "date_last_updated:${sort.format}"
            )
            if (locationsId.isNotEmpty()) parameter(
                "filter", "id:${locationsId.joinToString("|")}"
            )
        }
    }
}

private const val DetailsFieldList = "aliases,api_detail_url,count_of_location_appearances,deck," +
        "description,first_appeared_in_location,id,image,name,site_detail_url"

private const val ListFieldList =
    "api_detail_url,deck,id,image,name"