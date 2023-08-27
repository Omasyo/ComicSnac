package com.keetr.comicsnac.model.concept

import com.keetr.comicsnac.model.search.SearchModel

data class Concept(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
) : SearchModel
