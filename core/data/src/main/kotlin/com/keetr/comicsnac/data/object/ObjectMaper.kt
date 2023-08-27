package com.keetr.comicsnac.data.`object`

import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.network.search.models.ObjectListApiModel

internal fun ObjectListApiModel.toObjectItem() = ObjectItem(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)