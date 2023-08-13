package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.Api.appendDefaultParameters
import com.keetr.comicsnac.network.character.models.CharactersDetailsResponseApiModel
import com.keetr.comicsnac.network.character.models.CharactersListResponse
import com.keetr.comicsnac.network.character.models.ResultsApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class DefaultCharacterNetworkSource(
    private val client: HttpClient
) : CharacterNetworkSource {

    override suspend fun getCharacterDetails(apiUrl: String): CharactersDetailsResponseApiModel<ResultsApiModel> =
        client.get(apiUrl) {
            parameter("field_list", FieldList)
        }.body()

    override suspend fun getRecentCharacters(
        pageSize: Int,
        offset: Int
    ): CharactersListResponse = getCharacters(pageSize, offset, sortRecentlyUpdated = Sort.Descending)


    override suspend fun getAllCharacters(
        pageSize: Int,
        offset: Int,
        gender: Gender
    ): CharactersListResponse = getCharacters(pageSize, offset, gender)

    override suspend fun getCharactersWithId(
        pageSize: Int,
        offset: Int,
        characterIds: List<Int>
    ): CharactersListResponse = getCharacters(pageSize, offset, characterIds = characterIds)

    private suspend fun getCharacters(
        pageSize: Int,
        offset: Int,
        gender: Gender = Gender.All,
        characterIds: List<Int> = emptyList(),
        sortRecentlyUpdated: Sort = Sort.None
    ): CharactersListResponse = client.get("characters") {
        appendDefaultParameters()
        parameter("limit", pageSize)
        parameter("offset", offset)
        if (gender != Gender.All) parameter("gender", gender.id)
        if (characterIds.isNotEmpty()) parameter("filter", "id:${characterIds.joinToString("|")}")
        if (sortRecentlyUpdated != Sort.None) parameter(
            "sort",
            "date_last_updated:${sortRecentlyUpdated.format}"
        )
    }.body()
}

private const val FieldList = "aliases,api_detail_url,character_enemies,character_friends," +
        "count_of_issue_appearances,creators,deck,description,first_appeared_in_issue,gender," +
        "id,image,movies,name,origin,powers,publisher,real_name,site_detail_url,team_enemies," +
        "team_friends,teams,volume_credits"
