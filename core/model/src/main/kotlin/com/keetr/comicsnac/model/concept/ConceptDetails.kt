package com.keetr.comicsnac.model.concept

import com.keetr.comicsnac.model.issue.IssueBasic

data class ConceptDetails(
    val aliases: List<String>,
    val apiDetailUrl: String,
    val deck: String,
    val description: String,
    val firstAppearedInIssue: IssueBasic,
    val id: Int,
    val imageUrl: String,
    val issuesId: String,
    val name: String,
    val siteDetailUrl: String,
    val volumesId: List<Int>
)
