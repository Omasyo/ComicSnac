package com.keetr.comicsnac.data.publisher

import com.keetr.comicsnac.model.publisher.Publisher
import com.keetr.comicsnac.model.publisher.PublisherBasic
import com.keetr.comicsnac.model.publisher.PublisherDetails
import com.keetr.comicsnac.network.common.models.PublisherApiModel
import com.keetr.comicsnac.network.publisher.models.PublisherDetailsApiModel
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

internal fun List<PublisherListApiModel>.toPublishers() = map { apiModel -> apiModel.toPublisher() }

internal fun PublisherDetailsApiModel.toPublisherDetails() =
    PublisherDetails(
        aliases = aliases.split("\n"),
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        description = description ?: "",
        id = id,
        imageUrl = image.smallUrl,
        location = buildList {
            locationAddress?.let(::add)
            locationCity?.let(::add)
            locationState?.let(::add)
        }.joinToString(" "),
        name = name,
        siteDetailUrl = siteDetailUrl,
        teamsId = teams.map { it.id }

    )