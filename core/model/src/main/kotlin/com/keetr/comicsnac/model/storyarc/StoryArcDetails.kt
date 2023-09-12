package com.keetr.comicsnac.model.storyarc

import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic

data class StoryArcDetails(
    val apiDetailUrl: String,
    val deck: String,
    val description: String,
    val episodesId: List<Int>,
    val firstAppearedInEpisode: EpisodeBasic?,
    val firstAppearedInIssue: IssueBasic?,
    val id: Int,
    val imageUrl: String,
    val issuesId: List<Int>,
    val name: String,
    val publisher: PublisherBasic?,
    val siteDetailUrl: String
)
