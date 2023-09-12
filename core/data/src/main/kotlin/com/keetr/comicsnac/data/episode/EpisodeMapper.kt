package com.keetr.comicsnac.data.episode

import com.keetr.comicsnac.data.series.toSeriesBasic
import com.keetr.comicsnac.model.episode.Episode
import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.episode.EpisodeDetails
import com.keetr.comicsnac.network.common.models.EpisodeApiModel
import com.keetr.comicsnac.network.episode.models.EpisodeDetailsApiModel
import com.keetr.comicsnac.network.episode.models.EpisodeListApiModel

fun EpisodeApiModel.toEpisodeBasic() =
    EpisodeBasic(apiDetailUrl = apiDetailUrl, id = id, name = name)

fun List<EpisodeListApiModel>.toEpisodes() = map { apiModel -> apiModel.toEpisode() }

fun EpisodeListApiModel.toEpisode() =
    Episode(
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        id = id,
        imageUrl = image.smallUrl,
        name = name,
        seriesName = series.name
    )

fun EpisodeDetailsApiModel.toEpisodeDetails() =
    EpisodeDetails(
        airDate = airDate,
        apiDetailUrl = apiDetailUrl,
        charactersId = characterCredits.map { it.id },
        deck = deck ?: "",
        description = description ?: "",
        episodeNumber = episodeNumber,
        id = id,
        imageUrl = image.smallUrl,
        locationsId = locationCredits.map { it.id },
        name = name,
        objectsId = objectCredits.map { it.id },
        series = series.toSeriesBasic(),
        siteDetailUrl = siteDetailUrl,
        teamsId = teamCredits.map { it.id }
    )