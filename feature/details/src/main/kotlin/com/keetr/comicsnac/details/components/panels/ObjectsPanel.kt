package com.keetr.comicsnac.details.components.panels

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.objectsPanel(
    items: LazyPagingItems<ObjectItem>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {

    panel { index ->
        DetailsGrid(
            name = stringResource(R.string.objects),
            items = items,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }
        ) { objectItem ->
            ComicCard(
                modifier = Modifier.width(136f.dp),
                name = objectItem.name,
                imageUrl = objectItem.imageUrl,
                contentDescription = stringResource(
                    R.string.object_image_desc, objectItem.name
                ),
                onClick = { onItemClicked(objectItem.apiDetailUrl) })
        }
    }
}