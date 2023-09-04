package com.keetr.comicsnac.model.issue

import com.keetr.comicsnac.model.search.SearchModel

data class Issue(
    override val apiDetailUrl: String,
    override val deck: String,
    override val id: Int,
    override val imageUrl: String,
    val issueNumber: String,
    override val name: String,
    val volumeName: String
) : SearchModel
