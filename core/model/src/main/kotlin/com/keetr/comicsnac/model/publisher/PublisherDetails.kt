package com.keetr.comicsnac.model.publisher

data class PublisherDetails(
    val aliases: List<String>,
    val apiDetailUrl: String,
    val deck: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val location: String,
    val name: String,
    val siteDetailUrl: String,
    val teamsId: List<Int>
)
