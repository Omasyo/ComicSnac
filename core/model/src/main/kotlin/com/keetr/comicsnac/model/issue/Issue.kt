package com.keetr.comicsnac.model.issue

import com.keetr.comicsnac.model.search.SearchModel

data class Issue(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val issueNumber: String,
    val name: String,
    val volumeName: String
) : SearchModel
