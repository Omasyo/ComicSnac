package com.keetr.comicsnac.details.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder

@Composable
internal fun <T : Any> LazyItemScope.DetailsGrid(
    modifier: Modifier = Modifier,
    name: String,
    items: LazyPagingItems<T>,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    key: (item: T) -> Any,
    builder: @Composable (item: T) -> Unit
) {

    BackHandler(expanded) {
        onToggleExpand()
    }
    val heightModifier = remember(expanded) {
        if (expanded) Modifier.fillParentMaxHeight() else Modifier
            .height(320.dp)
    }
    Column(
        modifier
            .animateContentSize()
            .fillMaxWidth()
            .then(heightModifier)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16f.dp, top = 8f.dp)
            )
            Text(
                stringResource(if (expanded) R.string.collapse else R.string.expand),
                style = MaterialTheme.typography.titleMedium,
                color = if (expanded) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 8f.dp)
                    .clickable { onToggleExpand() }
                    .background(MaterialTheme.colorScheme.onSurface)
                    .padding(horizontal = 16f.dp, vertical = 4f.dp)
            )
        }

        AnimatedContent(
            targetState = items.loadState.refresh,
            label = "Category Carousel"
        ) { refreshState ->
            when (refreshState) {
                is LoadState.Error -> ErrorPlaceholder(heightModifier)
                LoadState.Loading -> LoadingPlaceholder(heightModifier)
                is LoadState.NotLoading -> {
                    LazyHorizontalGrid(
                        rows = GridCells.Adaptive(224f.dp),
                        contentPadding = PaddingValues(16f.dp, 8f.dp),
                        horizontalArrangement = Arrangement.spacedBy(8f.dp),
                        verticalArrangement = Arrangement.spacedBy(16f.dp)
                    ) {
                        items(items.itemCount, key = items.itemKey(key)) {
                            builder(items[it]!!)
                        }
                    }
                }
            }
            if (items.loadState.append == LoadState.Loading) {

            }
        }
    }
}