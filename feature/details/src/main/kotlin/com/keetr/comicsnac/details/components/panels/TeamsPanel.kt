package com.keetr.comicsnac.details.components.panels

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.R.string as CommonString

internal fun PanelLazyListScope.teamsPanel(
    items: LazyPagingItems<Team>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = teamsPanel(
    CommonString.teams,
    items,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.teamFriendsPanel(
    items: LazyPagingItems<Team>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = teamsPanel(
    R.string.team_friends,
    items,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.teamEnemiesPanel(
    items: LazyPagingItems<Team>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = teamsPanel(
    R.string.team_enemies,
    items,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

private fun PanelLazyListScope.teamsPanel(
    @StringRes nameResId: Int,
    items: LazyPagingItems<Team>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {
    panel { index ->
        DetailsGrid(name = stringResource(nameResId),
            items = items,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }) { team ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = team.name,
//                                imageUrl = team.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
        }
    }
}