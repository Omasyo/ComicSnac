package com.keetr.comicsnac.categories

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons

enum class LayoutType { Grid, List }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <T: Any> CategoryScreen(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit = {},
    layoutType: LayoutType,
    onToggleLayoutType: () -> Unit,
    items: LazyPagingItems<T>,
    listContentBuilder: @Composable LazyItemScope.(T) -> Unit,
    listGridBuilder: @Composable LazyGridItemScope.(T) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            AppIcons.ArrowBack,
                            stringResource(com.keetr.comicsnac.ui.R.string.back_button_desc)
                        )
                    }
                },
                actions = {
                    val isGrid = layoutType == LayoutType.Grid
                    Text(
                        stringResource(if (isGrid) R.string.show_details else R.string.hide_details),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isGrid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(top = 8f.dp)
                            .clickable { onToggleLayoutType() }
                            .background(MaterialTheme.colorScheme.onSurface)
                            .padding(horizontal = 16f.dp, vertical = 4f.dp)
                    )
                }
            )
        }
    ) { padding ->
        AnimatedContent(
            layoutType,
            label = "LayoutType",
            modifier = Modifier.padding(padding)
        ) { layoutType ->
            when (layoutType) {
                LayoutType.Grid -> {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(16f.dp),
                        columns = GridCells.Adaptive(96f.dp),
                        horizontalArrangement = Arrangement.spacedBy(16f.dp),
                        verticalArrangement = Arrangement.spacedBy(16f.dp)
                    ) {
                        items(100) {
                            listGridBuilder(items[it]!!)
                        }
                        if (items.loadState.append == LoadState.Loading) {
                            item {
                                LoadingPlaceholder()
                            }
                        }
                    }
                }

                LayoutType.List -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16f.dp),
                        verticalArrangement = Arrangement.spacedBy(16f.dp)
                    ) {
                        items(100) {
                            listContentBuilder(items[it]!!)
                        }
                        if (items.loadState.append == LoadState.Loading) {
                            item {
                                LoadingPlaceholder(Modifier.height(64f.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}