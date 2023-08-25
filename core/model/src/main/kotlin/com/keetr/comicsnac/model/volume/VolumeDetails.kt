package com.keetr.comicsnac.model.volume

import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic

data class VolumeDetails(
    val apiDetailUrl: String,
    val countOfIssues: Int,
    val deck: String,
    val description: String,
    val firstIssue: IssueBasic?,
    val id: Int,
    val imageUrl: String,
    val issuesId: List<Int>,
    val lastIssue: IssueBasic?,
    val name: String,
    val publisher: PublisherBasic?,
    val siteDetailUrl: String,
    val startYear: String
)
