package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.character.models.CharacterDetailsResponse
import com.keetr.comicsnac.network.character.models.CharactersListResponse
import com.keetr.comicsnac.network.common.Sort
import com.keetr.comicsnac.network.common.models.GenderApiModel
import com.keetr.comicsnac.network.makeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

const val TAG = "DefaultCharacterNetworkSource"

internal class DefaultCharacterNetworkSource @Inject constructor(
    private val client: HttpClient
) : CharacterNetworkSource {

    override suspend fun getCharacterDetails(id: String): Result<CharacterDetailsResponse> =
        makeRequest {
            client.get("character/4005-$id") {
                parameter("field_list", DetailsFieldList)
            }
        }

    override suspend fun getRecentCharacters(
        pageSize: Int, offset: Int
    ): Result<CharactersListResponse> =
        getCharacters(pageSize, offset, sortRecentlyUpdated = Sort.Descending)


    override suspend fun getAllCharacters(
        pageSize: Int, offset: Int, gender: GenderApiModel
    ): Result<CharactersListResponse> = getCharacters(pageSize, offset, gender)

    override suspend fun getCharactersWithId(
        pageSize: Int, offset: Int, characterIds: List<Int>
    ): Result<CharactersListResponse> = getCharacters(pageSize, offset, characterIds = characterIds)

    private suspend fun getCharacters(
        pageSize: Int,
        offset: Int,
        gender: GenderApiModel = GenderApiModel.All,
        characterIds: List<Int> = emptyList(),
        sortRecentlyUpdated: Sort = Sort.None
    ): Result<CharactersListResponse> = makeRequest {
        client.get("characters") {
            parameter("field_list", ListFieldList)
            parameter("limit", pageSize)
            parameter("offset", offset)
            if (gender != GenderApiModel.All) parameter("gender", gender.id)
            if (characterIds.isNotEmpty()) parameter(
                "filter", "id:${characterIds.joinToString("|")}"
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
    "api_detail_url,date_last_updated,deck,id,image,gender,name,site_detail_url"