package com.keetr.comicsnac.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.ui.components.cards.ComicCard

@Composable
fun CharacterWideCard(
    modifier: Modifier = Modifier,
    character: Character
) {
    ComicCard {

    }
}

@Preview
@Composable
private fun Preview() {
    CharacterWideCard(
        character = Character(
        apiDetailUrl = "https://search.yahoo.com/search?p=ne",
        deck = "contentiones",
        id = 5045,
        imageUrl = "http://www.bing.com/search?q=cubilia",
        name = "Leona Carey"
    ))
}