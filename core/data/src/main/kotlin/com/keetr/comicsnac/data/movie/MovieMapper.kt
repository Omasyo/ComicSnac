package com.keetr.comicsnac.data.movie

import com.keetr.comicsnac.data.person.toPersonBasic
import com.keetr.comicsnac.data.publisher.toPublisher
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.movie.MovieDetails
import com.keetr.comicsnac.network.movie.models.MovieDetailsApiModel
import com.keetr.comicsnac.network.movie.models.MovieListApiModel
import java.text.DecimalFormat

internal fun List<MovieListApiModel>.toMovies() = map { apiModel -> apiModel.toMovie() }

internal fun MovieListApiModel.toMovie() = Movie(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)

internal fun MovieDetailsApiModel.toMovieDetails() =
    MovieDetails(
        apiDetailUrl = apiDetailUrl,
        boxOfficeRevenue = boxOfficeRevenue?.formatToUsd() ?: "",
        budget = budget?.formatToUsd() ?: "",
        charactersId = characters.map { it.id },
        deck = deck ?: "",
        description = description ?: "",
        id = id,
        imageUrl = image.smallUrl,
        locationsId = locations.map { it.id },
        name = name,
        objectsId = objects.map { it.id },
        publishers = studios?.map { it.toPublisher() } ?: emptyList(),
        producers = producers?.map { it.toPersonBasic() } ?: emptyList(),
        rating = rating,
        releaseDate = releaseDate.toLocalDate(),
        runtime = runtime,
        siteDetailUrl = siteDetailUrl,
        teamsId = teams.map { it.id },
        totalRevenue = totalRevenue?.formatToUsd() ?: "",
        writers = writers?.map { it.toPersonBasic() } ?: emptyList()
    )

private fun String.formatToUsd(): String = when (val int = this.replace(",", "").toInt()) {
    0 -> ""
    in 1..999_999 -> DecimalFormat("\$ ###,###").format(this)
    in 1_000_000..999_999_999 -> {
        val million = int / 1_000_000f
        DecimalFormat("$ #.# Million").format(million)
    }

    else -> {
        val billion = int / 1_000_000_000f
        DecimalFormat("$ #.# Billion").format(billion)
    }
}