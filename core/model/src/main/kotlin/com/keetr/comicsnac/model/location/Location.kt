package com.keetr.comicsnac.model.location

import com.keetr.comicsnac.model.search.SearchModel

data class Location(
    override val apiDetailUrl: String,
    override val deck: String,
    override val id: Int,
    override val imageUrl: String,
    override val name: String
) : SearchModel
