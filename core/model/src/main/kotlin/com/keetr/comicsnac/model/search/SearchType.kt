package com.keetr.comicsnac.model.search

enum class SearchType(val format: String) {
    Character("character"),
    Concept("concept"),
    Object("object"),
    Location("location"),
    Issue("issue"),
    StoryArc("story_arc"),
    Volume("volume")
}