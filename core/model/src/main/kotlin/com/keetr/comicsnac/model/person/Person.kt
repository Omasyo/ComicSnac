package com.keetr.comicsnac.model.person

import com.keetr.comicsnac.model.search.SearchModel

data class Person(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
