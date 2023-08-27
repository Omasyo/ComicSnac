package com.keetr.comicsnac.model.location

import com.keetr.comicsnac.model.search.SearchModel

data class Location(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
