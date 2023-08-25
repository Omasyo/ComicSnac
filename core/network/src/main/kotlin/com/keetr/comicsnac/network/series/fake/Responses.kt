package com.keetr.comicsnac.network.series.fake

import java.io.File

internal const val root = "src/main/kotlin/com/keetr/comicsnac/network/series/fake/"

internal val SeriesResponse = File(root, "SeriesResponse.json").readText()

internal val SeriesDetailsResponse = File(root, "SeriesDetailsResponse.json").readText()