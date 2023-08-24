package com.keetr.comicsnac.model.power

data class PowerDetails(
    val apiDetailUrl: String,
    val characterIds: List<Int>,
    val description: String,
    val id: Int,
    val name: String,
    val siteDetailUrl: String
)
