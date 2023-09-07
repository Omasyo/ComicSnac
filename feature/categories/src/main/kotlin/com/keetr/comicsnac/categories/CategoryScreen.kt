package com.keetr.comicsnac.categories

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.model.LayoutType
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <T : Any> CategoryScreen(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit,
    layoutType: LayoutType,
    onToggleLayoutType: () -> Unit,
    items: LazyPagingItems<T>,
    listContentBuilder: @Composable LazyItemScope.(T) -> Unit,
    listGridBuilder: @Composable LazyGridItemScope.(T) -> Unit
) {
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.tertiary,
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
                            .testTag("layout_button")
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
            targetState = items.loadState.refresh,
            label = "CategoryContent",
            modifier = Modifier.padding(padding)
        ) { refreshState ->
            when (refreshState) {
                is LoadState.Error -> ErrorPlaceholder(Modifier.fillMaxSize()) { items.retry() }
                LoadState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                is LoadState.NotLoading -> AnimatedContent(
                    layoutType,
                    label = "LayoutType",
                    modifier = Modifier.testTag("category_content")
                ) { layoutType ->

                    when (layoutType) {
                        LayoutType.Grid -> {
                            LaunchedEffect(layoutType) {
                                gridState.scrollToItem(listState.firstVisibleItemIndex)
                            }
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16f.dp),
                                columns = GridCells.Adaptive(96f.dp),
                                horizontalArrangement = Arrangement.spacedBy(16f.dp),
                                verticalArrangement = Arrangement.spacedBy(16f.dp),
                                state = gridState
                            ) {
                                items(items.itemCount) {
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
                            LaunchedEffect(layoutType) {
                                listState.scrollToItem(gridState.firstVisibleItemIndex)
                            }
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16f.dp),
                                verticalArrangement = Arrangement.spacedBy(16f.dp),
                                state = listState
                            ) {
                                items(items.itemCount) {
                                    listContentBuilder(items[it]!!)
                                }
                                if (items.loadState.append == LoadState.Loading) {
                                    item {
                                        LoadingPlaceholder(
                                            Modifier
                                                .height(64f.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}