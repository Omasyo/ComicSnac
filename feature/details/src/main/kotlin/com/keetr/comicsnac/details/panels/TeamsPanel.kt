package com.keetr.comicsnac.details.panels

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.details.DetailsGrid
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.details.TeamsUiState
import com.keetr.comicsnac.ui.R.string as CommonString
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.teamsPanel(
    uiState: TeamsUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = teamsPanel(
    CommonString.teams,
    uiState,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.teamFriendsPanel(
    uiState: TeamsUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = teamsPanel(
    R.string.team_friends,
    uiState,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

internal fun PanelLazyListScope.teamEnemiesPanel(
    uiState: TeamsUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) = teamsPanel(
    R.string.team_enemies,
    uiState,
    expandedProvider,
    onToggleExpand,
    onItemClicked
)

private fun PanelLazyListScope.teamsPanel(
    @StringRes nameResId: Int,
    uiState: TeamsUiState,
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