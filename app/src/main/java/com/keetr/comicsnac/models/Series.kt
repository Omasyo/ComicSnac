package com.keetr.comicsnac.models

data class Series(
    val aliases: List<String>,
    val apiDetailUrl: String,
    val associatedImages: List<AssociatedImage>,
    val characters: List<CharacterCredit>,
//    val characterDiedIn: List<Any>,
    val concepts: List<Concept>,
    val coverDate: String,
    val dateAdded: String,
    val dateLastUpdated: String,
    val deck: String,
    val description: String,
    val id: Int,
    val image: Image,
    val issueNumber: String,
    val locationCredits: List<Location>,
    val name: String,
    val objectCredits: List<Object>,
    val personCredits: List<PersonCredit>,
    val siteDetailUrl: String,
    val storeDate: String,
    val storyArcCredits: List<Any>,
    val teamCredits: List<TeamCredit>,
    val volume: Volume
)