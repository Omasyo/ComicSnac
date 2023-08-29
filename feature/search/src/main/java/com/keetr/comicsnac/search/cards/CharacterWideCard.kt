package com.keetr.comicsnac.search.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.ui.R


@Composable
fun CharacterWideCard(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: (String) -> Unit
) = with(character) {
    WideCard(
        modifier = modifier,
        name = name,
        description = deck,
        onClick = { onClick(apiDetailUrl) },
        imageUrl = imageUrl,
        imageDescription = stringResource(R.string.character_image_desc, name),
        type = SearchType.Character.name,
    )
}