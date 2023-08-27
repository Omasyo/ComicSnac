package com.keetr.comicsnac.model.publisher

import com.keetr.comicsnac.model.search.SearchModel

data class Publisher(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
