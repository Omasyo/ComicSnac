package com.keetr.comicsnac.details.components.panels

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.details.CharactersUiState
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.friendsPanel(
    uiState: CharactersUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = charactersPanel(
    R.string.friends,
    uiState,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.enemiesPanel(
    uiState: CharactersUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = charactersPanel(
    R.string.friends,
    uiState,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

private fun PanelLazyListScope.charactersPanel(
    @StringRes nameResId: Int,
    uiState: CharactersUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {
    panel { index ->
        DetailsGrid(name = stringResource(nameResId),
            uiState = uiState,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }) { character ->
            ComicCard(modifier = Modifier.width(136f.dp),
                name = character.name,
                imageUrl = character.imageUrl,
                contentDescription = stringResource(
                    R.string.character_image_desc, character.name
                ),
                onClick = { onItemClicked(character.apiDetailUrl) })
        }
    }
}