package com.keetr.comicsnac.data.publisher

import com.keetr.comicsnac.model.publisher.Publisher
import com.keetr.comicsnac.model.publisher.PublisherBasic
import com.keetr.comicsnac.network.common.models.PublisherApiModel
import com.keetr.comicsnac.network.search.models.PublisherListApiModel

internal fun PublisherApiModel.toPublisherBasic() = PublisherBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)

internal fun PublisherListApiModel.toPublisher() = Publisher(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)