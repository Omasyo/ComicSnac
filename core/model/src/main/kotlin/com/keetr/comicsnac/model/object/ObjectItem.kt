package com.keetr.comicsnac.model.`object`

import com.keetr.comicsnac.model.search.SearchModel

data class ObjectItem(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
