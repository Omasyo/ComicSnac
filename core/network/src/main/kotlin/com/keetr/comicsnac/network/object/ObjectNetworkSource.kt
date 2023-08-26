package com.keetr.comicsnac.network.`object`

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.`object`.models.ObjectDetailsResponse
import com.keetr.comicsnac.network.`object`.models.ObjectListResponse

interface ObjectNetworkSource : NetworkSource {
    suspend fun getObjectDetails(id: String): Result<ObjectDetailsResponse>

    suspend fun getAllObjects(
        pageSize: Int,
        offset: Int,
        sortCoverDate: Sort = Sort.Descending
    ): Result<ObjectListResponse>

    suspend fun getObjectsWithId(
        pageSize: Int,
        offset: Int,
        sort: Sort = Sort.Descending,
        objectsId: List<Int>
    ): Result<ObjectListResponse>
}