package com.keetr.comicsnac.model.character

import com.keetr.comicsnac.model.search.SearchModel

data class Character(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
