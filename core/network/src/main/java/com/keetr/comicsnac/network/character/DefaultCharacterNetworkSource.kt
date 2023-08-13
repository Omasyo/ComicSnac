package com.keetr.comicsnac.network.character

import com.keetr.comicsnac.network.character.models.CharactersDetailsResponseApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

//class DefaultCharacterNetworkSource(
//    private val client: HttpClient
//) : CharacterNetworkSource {
//    override suspend fun getCharacter(characterId: Int): CharactersDetailsResponseApiModel =
//        client.get("/character") {
//            parameter("field_list", FieldList)
//        }.body()
//
//}

private const val FieldList = "aliases,api_detail_url,character_enemies,character_friends," +
        "count_of_issue_appearances,creators,deck,description,first_appeared_in_issue,gender," +
        "id,image,movies,name,origin,powers,publisher,real_name,site_detail_url,team_enemies," +
        "team_friends,teams,volume_credits"
