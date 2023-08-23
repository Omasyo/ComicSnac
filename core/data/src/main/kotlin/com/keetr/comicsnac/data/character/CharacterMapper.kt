package com.keetr.comicsnac.data.character

import com.keetr.comicsnac.data.issue.toIssueBasic
import com.keetr.comicsnac.data.origin.toOriginBasic
import com.keetr.comicsnac.data.people.toBasic
import com.keetr.comicsnac.data.power.toBasic
import com.keetr.comicsnac.data.publisher.toPublisherBasic
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterBasic
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.network.character.models.CharacterDetailsApiModel
import com.keetr.comicsnac.network.character.models.CharacterListApiModel
import com.keetr.comicsnac.network.common.models.CharacterApiModel

internal fun CharacterApiModel.toCharacterBasic() = CharacterBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)

internal fun List<CharacterListApiModel>.toCharacters() = map { apiModel -> apiModel.toCharacter() }

fun CharacterListApiModel.toCharacter() =
    Character(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name,
        siteDetailUrl = siteDetailUrl
    )

internal fun CharacterDetailsApiModel.toCharacterDetails() =
    CharacterDetails(
        id = id,
        aliases = aliases?.split('\n') ?: emptyList(),
        apiDetailUrl = apiDetailUrl,
        countOfIssueAppearances = countOfIssueAppearances,
        creators = creators.toBasic(),
        deck = deck ?: "",
        description = description ?: "",
        firstAppearance = firstAppearedInIssue.toIssueBasic(),
        enemiesId = characterEnemies.map { it.id },
        friendsId = characterFriends.map { it.id },
        gender = Gender.valueOf(gender.name),
        imageUrl = image.smallUrl,
        moviesId = movies.map { it.id },
        name = name,
        origin = origin?.toOriginBasic(),
        powers = powers.toBasic(),
        publisher = publisher?.toPublisherBasic(),
        realName = realName ?: "",
        siteDetailUrl = siteDetailUrl,
        teamEnemiesId = teamEnemies.map { it.id },
        teamFriendsId = teamFriends.map { it.id },
        teamsId = teams.map { it.id },
        volumeCreditsId = volumeCredits.map { it.id }
    )