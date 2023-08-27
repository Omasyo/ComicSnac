package com.keetr.comicsnac.model.team

import com.keetr.comicsnac.model.search.SearchModel

data class Team(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
) : SearchModel
