package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.ApiKey
import com.keetr.comicsnac.network.character.models.CharacterDetailsResponse
import com.keetr.comicsnac.network.character.models.CharactersListResponse
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class DefaultCharacterNetworkSource @Inject constructor(
    private val client: HttpClient
) : CharacterNetworkSource {

    override suspend fun getCharacterDetails(
        apiKey: String,
        id: String
    ): Result<CharacterDetailsResponse> =
        makeRequest {
            client.get("character/4005-$id") {
                parameter("api_key", apiKey)
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getRecentCharacters(
        apiKey: String, pageSize: Int, offset: Int
    ): Result<CharactersListResponse> =
        getCharacters(apiKey, pageSize, offset, sortRecentlyUpdated = Sort.Descending)


    override suspend fun getAllCharacters(
        apiKey: String, pageSize: Int, offset: Int, gender: GenderApiModel
    ): Result<CharactersListResponse> =
        getCharacters(apiKey, pageSize, offset, gender, sortRecentlyUpdated = Sort.Descending)

    override suspend fun getCharactersWithId(
        apiKey: String, pageSize: Int, offset: Int, characterIds: List<Int>
    ): Result<CharactersListResponse> =
        getCharacters(apiKey, pageSize, offset, charactersId = characterIds)

    private suspend fun getCharacters(
        apiKey: String,
        pageSize: Int,
        offset: Int,
        gender: GenderApiModel = GenderApiModel.All,
        charactersId: List<Int> = emptyList(),
        sortRecentlyUpdated: Sort = Sort.None
    ): Result<CharactersListResponse> = makeRequest {
        client.get("characters") {
            parameter("api_key", apiKey)
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (gender != GenderApiModel.All) parameter("gender", gender.id)
            if (charactersId.isNotEmpty()) parameter(
                "filter", "id:${charactersId.joinToString("|")}"
            )
            if (sortRecentlyUpdated != Sort.None) parameter(
                "sort", "date_last_updated:${sortRecentlyUpdated.format}"
            )
        }
    }
}

private const val DetailsFieldList = "aliases,api_detail_url,character_enemies,character_friends," +
        "count_of_issue_appearances,creators,deck,description,first_appeared_in_issue,gender," +
        "id,image,movies,name,origin,powers,publisher,real_name,site_detail_url,team_enemies," +
        "team_friends,teams,volume_credits"

private const val ListFieldList =
    "api_detail_url,deck,id,image,gender,name"