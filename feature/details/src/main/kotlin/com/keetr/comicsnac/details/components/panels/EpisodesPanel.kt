package com.keetr.comicsnac.details.components.panels

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.model.episode.Episode
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.episodesPanel(
    items: LazyPagingItems<Episode>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {

    panel { index ->
        DetailsGrid(
            name = stringResource(R.string.episodes),
            items = items,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }
        ) { episode ->
            PlainCard(
                modifier = Modifier.width(320f.dp),
                name = episode.name,
                imageUrl = episode.imageUrl,
                contentDescription = stringResource(
                    R.string.character_image_desc, episode.name
                ),
                onClick = { onItemClicked(episode.apiDetailUrl) })
        }
    }
}