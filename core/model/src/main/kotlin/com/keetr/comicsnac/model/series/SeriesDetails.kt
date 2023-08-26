package com.keetr.comicsnac.model.series

import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic

data class SeriesDetails(
    val apiDetailUrl: String,
    val charactersId: List<Int>,
    val episodeCount: Int,
    val deck: String,
    val description: String,
    val episodesId: List<Int>,
    val firstEpisode: EpisodeBasic,
    val id: Int,
    val imageUrl: String,
    val lastEpisode: EpisodeBasic,
    val name: String,
    val publisher: PublisherBasic,
    val siteDetailUrl: String,
    val startYear: String
)
