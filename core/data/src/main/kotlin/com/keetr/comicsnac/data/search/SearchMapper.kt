package com.keetr.comicsnac.data.search

import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.person.Person
import com.keetr.comicsnac.model.publisher.Publisher
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.network.search.models.CharacterListApiModel
import com.keetr.comicsnac.network.search.models.ConceptListApiModel
import com.keetr.comicsnac.network.search.models.IssueListApiModel
import com.keetr.comicsnac.network.search.models.LocationListApiModel
import com.keetr.comicsnac.network.search.models.ObjectListApiModel
import com.keetr.comicsnac.network.search.models.PersonListApiModel
import com.keetr.comicsnac.network.search.models.PublisherListApiModel
import com.keetr.comicsnac.network.search.models.SearchApiModel
import com.keetr.comicsnac.network.search.models.StoryArcListApiModel
import com.keetr.comicsnac.network.search.models.TeamListApiModel
import com.keetr.comicsnac.network.search.models.VolumeListApiModel

fun List<SearchApiModel>.toSearchModels() = map { apiModel -> apiModel.toSearchModel() }

fun SearchApiModel.toSearchModel() = when (this) {
    is CharacterListApiModel -> Character(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is ConceptListApiModel -> Concept(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is IssueListApiModel -> Issue(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        issueNumber = issueNumber,
        name = name ?: "",
        volumeName = volume.name
    )

    is LocationListApiModel -> Location(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is ObjectListApiModel -> ObjectItem(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is PersonListApiModel -> Person(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is PublisherListApiModel -> Publisher(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = ""
    )

    is StoryArcListApiModel -> StoryArc(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is TeamListApiModel -> Team(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

    is VolumeListApiModel -> Volume(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )
}