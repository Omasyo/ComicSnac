package com.keetr.comicsnac.network.person

import com.keetr.comicsnac.network.NetworkSource
import com.keetr.comicsnac.network.person.models.PersonDetailsResponse
import com.keetr.comicsnac.network.person.models.PersonListResponse

interface PersonNetworkSource : NetworkSource {
    suspend fun getPersonDetails(apiKey: String, id: String): Result<PersonDetailsResponse>

    suspend fun getAllPeople(
        apiKey: String,
        pageSize: Int,
        offset: Int,
    ): Result<PersonListResponse>
}