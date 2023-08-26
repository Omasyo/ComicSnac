package com.keetr.comicsnac.data.publisher

import com.keetr.comicsnac.model.publisher.PublisherBasic
import com.keetr.comicsnac.network.common.models.PublisherApiModel

internal fun PublisherApiModel.toPublisherBasic() = PublisherBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name

)