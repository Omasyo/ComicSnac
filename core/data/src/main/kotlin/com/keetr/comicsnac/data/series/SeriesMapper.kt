package com.keetr.comicsnac.data.series

import com.keetr.comicsnac.data.episode.toEpisodeBasic
import com.keetr.comicsnac.data.publisher.toPublisherBasic
import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic
import com.keetr.comicsnac.model.series.Series
import com.keetr.comicsnac.model.series.SeriesBasic
import com.keetr.comicsnac.model.series.SeriesDetails
import com.keetr.comicsnac.network.common.models.SeriesApiModel
import com.keetr.comicsnac.network.series.models.SeriesDetailsApiModel
import com.keetr.comicsnac.network.series.models.SeriesListApiModel

fun SeriesApiModel.toSeriesBasic() = SeriesBasic(apiDetailUrl = apiDetailUrl, id = id, name = name)

fun List<SeriesListApiModel>.toSeries() = map { apiModel -> apiModel.toSeries() }

fun SeriesListApiModel.toSeries() =
    Series(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name
    )

fun SeriesDetailsApiModel.toSeriesDetails() =
    SeriesDetails(
        apiDetailUrl = apiDetailUrl,
        charactersId = characters.map { it.id },
        episodeCount = countOfEpisodes,
        deck = deck ?: "",
        description = description ?: "",
        episodesId = episodes.map { it.id },
        firstEpisode = firstEpisode?.toEpisodeBasic(),
        id = id,
        imageUrl = image.smallUrl,
        lastEpisode = lastEpisode?.toEpisodeBasic(),
        name = name,
        publisher = publisher?.toPublisherBasic(),
        siteDetailUrl = siteDetailUrl,
        startYear = startYear ?: ""
    )