package com.keetr.comicsnac.data.location

import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.network.search.models.LocationListApiModel

internal fun LocationListApiModel.toLocation() = Location(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)