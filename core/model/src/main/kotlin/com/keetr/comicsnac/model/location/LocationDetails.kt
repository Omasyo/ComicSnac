package com.keetr.comicsnac.model.location

import com.keetr.comicsnac.model.issue.IssueBasic

data class LocationDetails(
    val apiDetailUrl: String,
    val countOfIssueAppearances: Int,
    val deck: String,
    val description: String,
    val firstAppearedInIssue: IssueBasic,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val siteDetailUrl: String
)
