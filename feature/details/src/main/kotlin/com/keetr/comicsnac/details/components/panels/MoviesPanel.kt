package com.keetr.comicsnac.details.components.panels

import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.details.MoviesUiState
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.moviesPanel(
    moviesUiState: MoviesUiState,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {

    panel { index ->
        DetailsGrid(
            name = stringResource(R.string.movies),
            uiState = moviesUiState,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }
        ) { movie ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = character.name,
//                                imageUrl = character.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
        }
    }
}