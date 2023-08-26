package com.keetr.comicsnac.home.fake

import com.keetr.comicsnac.model.character.Character

internal val Characters = List(30) {
    Character(
        apiDetailUrl = "http://www.bing.com/search?q=eius",
        deck = "tation",
        id = it,
        imageUrl = "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8759934-jrjrhr.jpg",
        name = "Beatriz Coleman"
    )
}