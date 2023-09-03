package com.keetr.comicsnac.network.`object`

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.`object`.models.ObjectDetailsResponse
import com.keetr.comicsnac.network.`object`.models.ObjectListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultObjectNetworkSource @Inject constructor(
    private val client: HttpClient
) : ObjectNetworkSource {
    override suspend fun getObjectDetails(
        apiKey: String,
        id: String
    ): Result<ObjectDetailsResponse> =
        makeRequest {
            client.get("object/4055-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllObjects(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort
    ): Result<ObjectListResponse> =
        getObjects(apiKey, pageSize, offset, sortCoverDate)

    override suspend fun getObjectsWithId(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sort: Sort,
        objectsId: List<Int>
    ): Result<ObjectListResponse> = getObjects(apiKey, pageSize, offset, sort, objectsId)

    private suspend fun getObjects(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        sort: Sort = Sort.None,
        objectsId: List<Int> = emptyList()
    ): Result<ObjectListResponse> = makeRequest {
        client.get("objects") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (sort != Sort.None) parameter(
                "sort", "date_last_updated:${sort.format}"
            )
            if (objectsId.isNotEmpty()) parameter(
                "filter", "id:${objectsId.joinToString("|")}"
            )
        }
    }
}

private const val DetailsFieldList = "aliases,api_detail_url,count_of_issue_appearances,deck," +
        "description,first_appeared_in_issue,id,image,name,site_detail_url"

private const val ListFieldList =
    "api_detail_url,deck,id,image,name"