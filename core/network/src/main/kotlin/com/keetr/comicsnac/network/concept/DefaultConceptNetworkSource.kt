package com.keetr.comicsnac.network.concept

import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.concept.models.ConceptDetailsResponse
import com.keetr.comicsnac.network.concept.models.ConceptListResponse
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultConceptNetworkSource @Inject constructor(
    private val client: HttpClient
) : ConceptNetworkSource {
    override suspend fun getConceptDetails(
        apiKey: String,
        id: String
    ): Result<ConceptDetailsResponse> =
        makeRequest {
            client.get("concept/4015-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getAllConcepts(
        apiKey: String,
        pageSize: Int,
        offset: Int
    ): Result<ConceptListResponse> =
        makeRequest {
            client.get("concepts") {
                parameter("api_key", apiKey)
                parameter("field_list", ListFieldList)
                parameter("limit", pageSize)
                parameter("offset", offset)
                parameter("sort", "date_last_updated:${Sort.Descending.format}")
            }
        }
}


private const val DetailsFieldList = "aliases,api_detail_url,deck,description," +
        "first_appeared_in_issue,id,image,issue_credits,name,site_detail_url,start_year," +
        "volume_credits"

private const val ListFieldList = "api_detail_url,deck,id,image,name"