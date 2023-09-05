package com.keetr.comicsnac.details.components.panels

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.components.DetailsGrid
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope

internal fun PanelLazyListScope.volumesPanel(
    items: LazyPagingItems<Volume>,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {

    panel { index ->
        DetailsGrid(
            modifier = Modifier.testTag("volumes_panel"),
            name = stringResource(R.string.volumes),
            items = items,
            expanded = expandedProvider(index),
            onToggleExpand = {
                onToggleExpand(index)
            },
            key = { it.id }
        ) { volume ->
            PlainCard(
                modifier = Modifier.width(136f.dp),
                name = volume.name,
                imageUrl = volume.imageUrl,
                contentDescription = stringResource(
                    R.string.volume_image_desc, volume.name
                ),
                onClick = { onItemClicked(volume.apiDetailUrl) })
        }
    }
}