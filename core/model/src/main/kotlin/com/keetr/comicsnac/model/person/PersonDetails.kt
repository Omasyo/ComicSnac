package com.keetr.comicsnac.model.person

import java.time.LocalDate

data class PersonDetails(
    val apiDetailUrl: String,
    val birth: LocalDate?,
    val createdCharactersId: List<Int>,
    val death: LocalDate?,
    val deck: String,
    val description: String,
    val email: String,
    val hometown: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val siteDetailUrl: String,
    val volumesId: List<Int>,
    val website: String
)
