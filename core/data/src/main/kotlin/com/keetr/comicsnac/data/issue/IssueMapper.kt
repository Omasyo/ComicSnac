package com.keetr.comicsnac.data.issue

import com.keetr.comicsnac.data.concept.toBasic
import com.keetr.comicsnac.data.person.toCredits
import com.keetr.comicsnac.data.volume.toBasic
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.issue.IssueDetails
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.issue.models.IssueDetailsApiModel
import com.keetr.comicsnac.network.search.models.IssueListApiModel

internal fun IssueApiModel.toIssueBasic() = IssueBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name ?: ""
)

internal fun List<IssueListApiModel>.toIssues() = map { apiModel -> apiModel.toIssue() }

internal fun IssueListApiModel.toIssue() = Issue(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    issueNumber = issueNumber,
    name = name ?: "",
    volumeName = volume.name
)

internal fun IssueDetailsApiModel.toIssueDetails() = IssueDetails(
    apiDetailUrl = apiDetailUrl,
    associatedImagesUrl = associatedImages.map { it.originalUrl },
    credits = personCredits.toCredits(),
    charactersId = characterCredits.map { it.id },
    concepts = conceptCredits.toBasic(),
    coverDate = coverDate,
    deck = deck ?: "",
    description = description ?: "",
    id = id,
    imageUrl = image.smallUrl,
    issueNumber = issueNumber,
    locationsId = locationCredits.map { it.id },
    name = name ?: "",
    objectsId = objectCredits.map { it.id },
    siteDetailUrl = siteDetailUrl,
    storeDate = storeDate,
    storyArcsId = storyArcCredits.map { it.id },
    teamsId = teamCredits.map { it.id },
    volume = volume.toBasic()

)