package com.keetr.comicsnac.model.episode

import com.keetr.comicsnac.model.series.SeriesBasic

data class Episode(
    val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val seriesName: String
)
