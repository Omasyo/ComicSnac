package com.keetr.comicsnac.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.model.search.SearchModel
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.ui.components.TextField
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    queryProvider: () -> String,
    onQueryChanged: (String) -> Unit,
    searchEmpty: Boolean,
    filter: Set<SearchType>,
    onClickFilter: (SearchType) -> Unit,
    onLongClickFilter: (SearchType) -> Unit,
    onItemClicked: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    onBackPressed: () -> Unit,
    searchResults: LazyPagingItems<SearchModel>
) {
    BottomSheetScaffold(
        modifier = modifier,
        sheetTonalElevation = 0f.dp,
        sheetShadowElevation = 0f.dp,
        sheetShape = RectangleShape,
        sheetContent = {
            FlowRow(
                Modifier.padding(24f.dp).navigationBarsPadding().imePadding(),
                verticalArrangement = Arrangement.spacedBy(8f.dp),
                horizontalArrangement = Arrangement.spacedBy(8f.dp)
            ) {
                SearchType.entries.forEach { type ->
                    SearchFilter(
                        name = type.name,
                        enabled = filter.contains(type),
                        onClick = { onClickFilter(type) },
                        onLongClick = { onLongClickFilter(type) }
                    )
                }
            }
        },
//        sheetPeekHeight = 24f.dp,
        containerColor = MaterialTheme.colorScheme.tertiary,
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            TopBar(
                modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 64f.dp)
                    .padding(horizontal = 12f.dp),
                queryProvider = queryProvider,
                onQueryChanged = onQueryChanged,
                onSearch = onSearch,
                onBackPressed = onBackPressed,
                onClear = onClear
            )
            AnimatedContent(
                searchEmpty,
                label = "Search content",
                modifier = Modifier.weight(1f)
            ) { searchEmpty ->
                if (searchEmpty) Box(
                    modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "What would you like to know?",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(16f.dp)
                    )
                }
                else
                    AnimatedContent(
                        targetState = searchResults.loadState.refresh,
                        label = "Search content"
                    ) { refreshState ->
                        when (refreshState) {
                            is LoadState.Error -> ErrorPlaceholder(Modifier.fillMaxSize()) {
                                searchResults.refresh()
                            }

                            LoadState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                            is LoadState.NotLoading -> {
                                if (searchResults.itemSnapshotList.isEmpty()) {
                                    Box(
                                        modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            stringResource(R.string.no_results),
                                            style = MaterialTheme.typography.headlineLarge,
                                        )
                                    }
                                }
                                LazyColumn(
                                    modifier = Modifier.testTag("search_results"),
                                    contentPadding = PaddingValues(16f.dp),
                                    verticalArrangement = Arrangement.spacedBy(16f.dp)
                                ) {
                                    items(
                                        searchResults.itemCount,
                                        key = { searchResults[it]!!.apiDetailUrl }
                                    ) {
                                        ItemWideCard(
                                            item = searchResults[it]!!,
                                            onItemClicked = onItemClicked
                                        )
                                    }
                                    if (searchResults.loadState.append == LoadState.Loading) {
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

@Composable
private fun TopBar(
    modifier: Modifier,
    queryProvider: () -> String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onBackPressed: () -> Unit,
    onClear: () -> Unit
) {
    val query = queryProvider()
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(AppIcons.ArrowBack, null)
        }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = Modifier
                .testTag("search_field")
                .padding(horizontal = 8f.dp)
                .weight(1f),
            value = query,
            onValueChange = onQueryChanged,
            textStyle = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onSurface),
            placeholder = stringResource(R.string.search_placeholder),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    focusManager.clearFocus()
                }
            ),
        )
        AnimatedVisibility(query.isNotEmpty()) {
            IconButton(onClick = onClear) {
                Icon(AppIcons.Close, null)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchFilter(
    name: String,
    enabled: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    with(MaterialTheme.colorScheme) {
        val containerColor by animateColorAsState(
            if (enabled) onSurface else secondary,
            label = "search_filter_container"
        )
        val contentColor by animateColorAsState(
            if (enabled) secondary else onSurface,
            label = "search_filter_content"
        )
        Text(
            text = name,
            color = contentColor,
            modifier = Modifier
                .combinedClickable(
                    onLongClick = onLongClick,
                    onClick = onClick
                )
                .border(4f.dp, outline, RectangleShape)
                .background(containerColor)
                .padding(horizontal = 16f.dp, vertical = 8f.dp)
        )
    }
}


//@Preview
//@Composable
//private fun Preview() {
//    ComicSnacTheme {
//        var query by remember {
//            mutableStateOf("")
//        }
//        var filter by remember {
//            mutableStateOf(setOf(SearchType.Character, SearchType.Concept))
//        }
//        SearchScreen(
//            query = query,
//            onQueryChanged = { query = it },
//            onItemClicked = {},
//            filter = filter,
//            onFilterChange = {
//                filter = if (filter.contains(it)) filter - it else filter + it
//            },
//            onBackPressed = {},
//            searchResults = List(30) {
//                Character(
//                    apiDetailUrl = "https://search.yahoo.com/search?p=solet",
//                    deck = "sententiae",
//                    id = 2909,
//                    imageUrl = "https://www.google.com/#q=nonumes",
//                    name = "Berta Shelton"
//                )
//            }
//
//        )
//    }
//}