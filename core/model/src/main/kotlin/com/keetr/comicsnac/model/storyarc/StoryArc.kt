package com.keetr.comicsnac.model.storyarc

import com.keetr.comicsnac.model.search.SearchModel

data class StoryArc(
    override val apiDetailUrl: String,
    override val deck: String,
    override val id: Int,
    override val imageUrl: String,
    override val name: String
) : SearchModel
