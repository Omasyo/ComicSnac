package com.keetr.comicsnac.search

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.search.SearchModel
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.search.cards.CharacterWideCard
import com.keetr.comicsnac.search.cards.ConceptWideCard
import com.keetr.comicsnac.search.cards.IssueWideCard
import com.keetr.comicsnac.search.cards.LocationWideCard
import com.keetr.comicsnac.search.cards.ObjectWideCard
import com.keetr.comicsnac.search.cards.StoryArcWideCard
import com.keetr.comicsnac.search.cards.VolumeWideCard
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    searchEmpty: Boolean,
    filter: Set<SearchType>,
    onFilterChange: (SearchType) -> Unit,
    onItemClicked: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClear: () -> Unit,
    onBackPressed: () -> Unit,
    searchResults: LazyPagingItems<SearchModel>
) {
    BottomSheetScaffold(
        modifier = modifier.imePadding(),
        sheetTonalElevation = 0f.dp,
        sheetShadowElevation = 0f.dp,
        sheetShape = RectangleShape,
        sheetContent = {
            FlowRow(
                Modifier.padding(24f.dp),
                verticalArrangement = Arrangement.spacedBy(8f.dp),
                horizontalArrangement = Arrangement.spacedBy(8f.dp)
            ) {
                SearchType.entries.forEach { type ->
                    SearchFilter(
                        name = type.name,
                        enabled = filter.contains(type),
                        onStatusChange = { onFilterChange(type) }
                    )
                }
            }
        },
//        sheetPeekHeight = 24f.dp,
        containerColor = MaterialTheme.colorScheme.tertiary,
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 64f.dp)
                    .padding(horizontal = 12f.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(AppIcons.ArrowBack, null)
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChanged,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8f.dp)
                        .weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch(query)
                        }
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                )
                AnimatedVisibility(query.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(AppIcons.Close, null)
                    }
                }
            }
            AnimatedContent(searchEmpty, label = "Search content", modifier = Modifier.weight(1f)) { searchEmpty ->
                if (searchEmpty) Box(
                    modifier
                        .fillMaxSize()
                ) {
                    Text(
                        "What would you like to know?",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else
                    AnimatedContent(
                        targetState = searchResults.loadState.refresh,
                        label = "Search content"
                    ) { refreshState ->
                        when (refreshState) {
                            is LoadState.Error -> ErrorPlaceholder {
                                searchResults.refresh()
                            }

                            LoadState.Loading -> LoadingPlaceholder()
                            is LoadState.NotLoading -> {
                                LazyColumn(
                                    contentPadding = PaddingValues(16f.dp),
                                    verticalArrangement = Arrangement.spacedBy(16f.dp)
                                ) {
                                    items(searchResults.itemCount /* TODO key = searchResults.itemKey {  }*/) {
                                        when (val result = searchResults[it]) {
                                            is Character -> CharacterWideCard(
                                                character = result,
                                                onClick = onItemClicked
                                            )

                                            is Concept -> ConceptWideCard(
                                                concept = result,
                                                onClick = onItemClicked
                                            )

                                            is ObjectItem -> ObjectWideCard(
                                                objectItem = result,
                                                onClick = onItemClicked
                                            )

                                            is Location -> LocationWideCard(
                                                location = result,
                                                onClick = onItemClicked
                                            )

                                            is Issue -> IssueWideCard(
                                                issue = result,
                                                onClick = onItemClicked
                                            )

                                            is StoryArc -> StoryArcWideCard(
                                                storyArc = result,
                                                onClick = onItemClicked
                                            )

                                            is Volume -> VolumeWideCard(
                                                volume = result,
                                                onClick = onItemClicked
                                            )

                                            else -> throw NotImplementedError("Unknown type $result")
                                        }
                                    }
                                    if (searchResults.loadState.append == LoadState.Loading) {
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
    }
}

@Composable
private fun SearchFilter(
    name: String,
    enabled: Boolean,
    onStatusChange: (Boolean) -> Unit
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
                .clickable { onStatusChange(!enabled) }
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