package com.keetr.comicsnac.model.episode

import com.keetr.comicsnac.model.series.SeriesBasic
import java.time.LocalDate

data class EpisodeDetails(
    val airDate: LocalDate,
    val apiDetailUrl: String,
    val charactersId: List<Int>,
    val deck: String,
    val description: String,
    val episodeNumber: String,
    val id: Int,
    val imageUrl: String,
    val locationsId: List<Int>,
    val name: String,
    val objectsId: List<Int>,
    val series: SeriesBasic,
    val siteDetailUrl: String,
    val teamsId: List<Int>
)
