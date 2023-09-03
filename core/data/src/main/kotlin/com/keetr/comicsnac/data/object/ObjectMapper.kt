package com.keetr.comicsnac.data.`object`

import com.keetr.comicsnac.data.issue.toIssueBasic
import com.keetr.comicsnac.model.`object`.ObjectDetails
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.network.`object`.models.ObjectDetailsApiModel
import com.keetr.comicsnac.network.search.models.ObjectListApiModel

internal fun List<ObjectListApiModel>.toObjectItems() = map { apiModel -> apiModel.toObjectItem() }

internal fun ObjectListApiModel.toObjectItem() = ObjectItem(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)

internal fun ObjectDetailsApiModel.toObjectDetails() =
    ObjectDetails(
        aliases = aliases?.split("\n") ?: emptyList(),
        apiDetailUrl = apiDetailUrl,
        countOfIssueAppearances = countOfIssueAppearances,
        deck = deck ?: "",
        description = description ?: "",
        firstAppearedInIssue = firstAppearedInIssue?.toIssueBasic(),
        id = id,
        imageUrl = image.smallUrl,
        name = name,
        siteDetailUrl = siteDetailUrl
    )