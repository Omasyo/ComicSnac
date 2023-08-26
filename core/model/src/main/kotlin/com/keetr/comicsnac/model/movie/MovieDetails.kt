package com.keetr.comicsnac.model.movie

import com.keetr.comicsnac.model.person.PersonBasic
import com.keetr.comicsnac.model.publisher.PublisherBasic

data class MovieDetails(
    val apiDetailUrl: String,
    val boxOfficeRevenue: String,
    val budget: String,
    val charactersId: List<Int>,
    val deck: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val locationsId: List<Int>,
    val name: String,
    val objects: List<Int>,
    val publishers: List<PublisherBasic>,
    val producers: List<PersonBasic>,
    val rating: String,
    val releaseDate: String,
    val runtime: String,
    val siteDetailUrl: String,
    val teamsId: List<Int>,
    val totalRevenue: String,
    val writers: List<PersonBasic>
)
