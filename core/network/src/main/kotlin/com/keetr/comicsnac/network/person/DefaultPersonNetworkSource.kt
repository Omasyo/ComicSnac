package com.keetr.comicsnac.network.person

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.makeRequest
import com.keetr.comicsnac.network.person.models.PersonDetailsResponse
import com.keetr.comicsnac.network.person.models.PersonListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DefaultPersonNetworkSource @Inject constructor (
    private val client: HttpClient
) : PersonNetworkSource {
    override suspend fun getPersonDetails(id: String): Result<PersonDetailsResponse> =
        makeRequest {
            client.get("person/4040-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllPeople(
        pageSize: Int,
        offset: Int,
    ): Result<PersonListResponse> = makeRequest {
        client.get("people") {
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            parameter("sort", "date_last_updated:${Sort.Descending.format}")
        }
    }
}


private const val DetailsFieldList =
    "api_detail_url,birth,created_characters,death,deck,description,email,gender,hometown,id," +
            "image,name,site_detail_url,volume_credits,website"

private const val ListFieldList = "api_detail_url,deck,id,image,name,site_detail_url"
