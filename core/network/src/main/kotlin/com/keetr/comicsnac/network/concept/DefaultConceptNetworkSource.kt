package com.keetr.comicsnac.network.concept

import com.keetr.comicsnac.network.Api.appendDefaultParameters
import com.keetr.comicsnac.network.concept.models.ConceptDetailsResponse
import com.keetr.comicsnac.network.concept.models.ConceptListResponse
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

const val TAG = "DefaultConceptNetworkSource"

internal class DefaultConceptNetworkSource @Inject constructor(
    private val client: HttpClient
) : ConceptNetworkSource {
    override suspend fun getConceptDetails(apiUrl: String): Result<ConceptDetailsResponse> =
        makeRequest(TAG) {
            client.get(apiUrl) {
                appendDefaultParameters()
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllConcepts(pageSize: Int, offset: Int): Result<ConceptListResponse> =
        makeRequest(TAG) {
            client.get("concepts") {
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
            }
        }
}


private const val DetailsFieldList = "aliases,api_detail_url,deck,description," +
        "first_appeared_in_issue,id,image,name,site_detail_url,start_year,volume_credits"

private const val ListFieldList = "api_detail_url,deck,id,image,name,site_detail_url"