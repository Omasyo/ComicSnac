package com.keetr.comicsnac.data.storyarc

import com.keetr.comicsnac.data.episode.toEpisodeBasic
import com.keetr.comicsnac.data.issue.toIssueBasic
import com.keetr.comicsnac.data.publisher.toPublisherBasic
import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.storyarc.StoryArcDetails
import com.keetr.comicsnac.network.search.models.StoryArcListApiModel
import com.keetr.comicsnac.network.storyarc.models.StoryArcDetailsApiModel

internal fun List<StoryArcListApiModel>.toStoryArcs() = map { apiModel -> apiModel.toStoryArc() }

internal fun StoryArcListApiModel.toStoryArc() = StoryArc(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)

internal fun StoryArcDetailsApiModel.toStoryArcDetails() =
    StoryArcDetails(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        description = description ?: "",
        episodesId = episodes?.map { it.id } ?: emptyList(),
        firstAppearedInEpisode = firstAppearedInEpisode?.toEpisodeBasic(),
        firstAppearedInIssue = firstAppearedInIssue?.toIssueBasic(),
        id = id,
        imageUrl = image.smallUrl,
        issuesId = issues.map { it.id },
        name = name,
        publisher = publisher?.toPublisherBasic(),
        siteDetailUrl = siteDetailUrl
    )