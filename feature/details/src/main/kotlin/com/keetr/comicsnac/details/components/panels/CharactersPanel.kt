package com.keetr.comicsnac.details.components.panels

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.ui.R.string as CommonString
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.friendsPanel(
    items: LazyPagingItems<Character>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = charactersPanel(
    R.string.friends,
    items,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.enemiesPanel(
    items: LazyPagingItems<Character>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = charactersPanel(
    R.string.enemies,
    items,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.charactersPanel(
    @StringRes nameResId: Int,
    items: LazyPagingItems<Character>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {
    panel { index ->
        DetailsGrid(
            modifier = Modifier.testTag("characters_panel"),
            name = stringResource(nameResId),
            items = items,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }) { character ->
            ComicCard(modifier = Modifier.width(136f.dp),
                name = character.name,
                imageUrl = character.imageUrl,
                contentDescription = stringResource(
                    CommonString.character_image_desc, character.name
                ),
                onClick = { onItemClicked(character.apiDetailUrl) })
        }
    }
}