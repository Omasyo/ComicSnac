package com.keetr.comicsnac.model.volume

import com.keetr.comicsnac.model.search.SearchModel

data class Volume(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
