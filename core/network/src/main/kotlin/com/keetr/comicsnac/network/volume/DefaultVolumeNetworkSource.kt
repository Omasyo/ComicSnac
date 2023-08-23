package com.keetr.comicsnac.network.volume

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.volume.models.VolumeDetailsResponse
import com.keetr.comicsnac.network.volume.models.VolumeListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultVolumeNetworkSource @Inject constructor(
    private val client: HttpClient
) : VolumeNetworkSource {
    override suspend fun getVolumeDetails(id: String): Result<VolumeDetailsResponse> =
        makeRequest {
            client.get("volume/4050-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllVolumes(pageSize: Int, offset: Int): Result<VolumeListResponse> =
        getVolumes(pageSize, offset)

    override suspend fun getVolumesWithId(
        pageSize: Int,
        offset: Int,
        volumeIds: List<Int>
    ): Result<VolumeListResponse> = getVolumes(pageSize, offset, volumeIds = volumeIds)

    private suspend fun getVolumes(
        pageSize: Int,
        offset: Int,
        sort: Sort = Sort.Descending,
        volumeIds: List<Int> = emptyList()
    ): Result<VolumeListResponse> =
        makeRequest {
            client.get("volumes") {
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
                parameter("sort", "date_last_updated:${sort.format}")
                if (volumeIds.isNotEmpty()) parameter(
                    "filter", "id:${volumeIds.joinToString("|")}"
                )
            }
        }
}

private const val DetailsFieldList =
    "api_detail_url,count_of_issues,deck,description,first_issue,id,image,last_issue,name," +
            "publisher,site_detail_url,start_year"

private const val ListFieldList = "api_detail_url,deck,id,image,name,site_detail_url"