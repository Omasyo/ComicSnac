package com.keetr.comicsnac.details.components.panels

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.storyArcsPanel(
    items: LazyPagingItems<StoryArc>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {

    panel { index ->
        DetailsGrid(
            name = stringResource(R.string.story_arcs),
            items = items,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }
        ) { storyArcs ->
            ComicCard(
                modifier = Modifier.width(136f.dp),
                name = storyArcs.name,
                imageUrl = storyArcs.imageUrl,
                contentDescription = stringResource(
                    R.string.story_arc_image_desc, storyArcs.name
                ),
                onClick = { onItemClicked(storyArcs.apiDetailUrl) })
        }
    }
}