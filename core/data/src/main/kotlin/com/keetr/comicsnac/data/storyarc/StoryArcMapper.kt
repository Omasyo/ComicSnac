package com.keetr.comicsnac.data.storyarc

import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.network.search.models.StoryArcListApiModel

internal fun StoryArcListApiModel.toStoryArc() = StoryArc(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)