package com.keetr.comicsnac.model.person

import com.keetr.comicsnac.model.search.SearchModel

data class Person(
    override val apiDetailUrl: String,
    override val deck: String,
    override val id: Int,
    override val imageUrl: String,
    override val name: String
) : SearchModel
