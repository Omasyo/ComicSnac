package com.keetr.comicsnac.model.storyarc

import com.keetr.comicsnac.model.search.SearchModel

data class StoryArc(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String
) : SearchModel
