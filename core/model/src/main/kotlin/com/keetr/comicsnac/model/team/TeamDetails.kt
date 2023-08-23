package com.keetr.comicsnac.model.team

import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic

data class TeamDetails(
    val aliases: List<String>,
    val characterEnemiesId: List<Int>,
    val characterFriendsId: List<Int>,
    val charactersId: List<Int>,
    val countOfMembers: Int,
    val deck: String,
    val description: String,
    val firstAppearedInIssue: IssueBasic,
    val id: Int,
    val imageUrl: String,
    val moviesId: List<Int>,
    val name: String,
    val publisher: PublisherBasic?,
    val siteDetailUrl: String,
    val volumeCreditsId: List<Int>
)
