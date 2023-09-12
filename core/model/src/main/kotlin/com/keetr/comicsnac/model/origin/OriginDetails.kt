package com.keetr.comicsnac.model.origin

data class OriginDetails(
    val apiDetailUrl: String,
    val charactersId: List<Int>,
    val id: Int,
    val name: String,
    val siteDetailUrl: String
)
