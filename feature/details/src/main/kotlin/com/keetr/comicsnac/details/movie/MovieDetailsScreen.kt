package com.keetr.comicsnac.details.movie

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Domain
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.components.DetailsErrorPlaceholder
import com.keetr.comicsnac.details.components.DetailsFlow
import com.keetr.comicsnac.details.components.DetailsLoadingPlaceholder
import com.keetr.comicsnac.details.components.DetailsScreen
import com.keetr.comicsnac.details.components.Image
import com.keetr.comicsnac.details.components.Info
import com.keetr.comicsnac.details.components.panels.charactersPanel
import com.keetr.comicsnac.details.components.panels.locationsPanel
import com.keetr.comicsnac.details.components.panels.objectsPanel
import com.keetr.comicsnac.details.components.panels.teamsPanel
import com.keetr.comicsnac.details.components.panels.webViewPanel
import com.keetr.comicsnac.details.components.shareUrl
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.movie.MovieDetails
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.components.webview.rememberComicWebViewContent
import kotlinx.coroutines.launch
import com.keetr.comicsnac.ui.R.string as CommonString

@Composable
internal fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: DetailsUiState<MovieDetails>,
    characters: LazyPagingItems<Character>,
    locations: LazyPagingItems<Location>,
    objects: LazyPagingItems<ObjectItem>,
    teams: LazyPagingItems<Team>
) {
    when (detailsUiState) {
        is Error -> {
            DetailsErrorPlaceholder(onBackPressed = onBackPressed)
        }

        Loading -> {
            DetailsLoadingPlaceholder(onBackPressed = onBackPressed)
        }

        is Success -> {
            val scope = rememberCoroutineScope()

            var imageExpanded by rememberSaveable {
                mutableStateOf(false)
            }

            val state = rememberLazyListState()
            var expandedIndex by rememberSaveable {
                mutableIntStateOf(-1)
            }
            val canScroll = expandedIndex < 0 && !imageExpanded

            fun expandedProviderCallback(index: Int) = index == expandedIndex


            fun onExpand(index: Int) {
                scope.launch {
                    if (expandedIndex == index) {
                        expandedIndex = -1
                        state.animateScrollAndAlignItem(
                            index, 0.33f
                        )
                    } else {
                        expandedIndex = index
                        state.animateScrollAndAlignItem(
                            index
                        )
                    }
                }
            }

            BackHandler(!canScroll) {
                imageExpanded = false
            }

            with(detailsUiState.content) {

                val webViewContent =
                    rememberComicWebViewContent(description, Domain, onItemClicked)
                val context = LocalContext.current

                DetailsScreen(
                    modifier = modifier,
                    images = listOf(
                        Image(imageUrl, stringResource(CommonString.issue_image_desc))
                    ),
                    lazyListState = state,
                    userScrollEnabled = canScroll,
                    onBackPressed = onBackPressed,
                    onImageClose = { imageExpanded = false },
                    imageExpanded = imageExpanded,
                    onImageClicked = {
                        scope.launch {
                            imageExpanded = true
                            state.scrollToItem(0)
                        }
                    },
                    onShareClick = { shareUrl(context, siteDetailUrl) }
                ) {
                    panel {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16f.dp, vertical = 8f.dp),
                            verticalArrangement = Arrangement.spacedBy(4f.dp),
                        ) {
                            Text(name, style = MaterialTheme.typography.headlineMedium)
                            Text(deck, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    panel {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16f.dp, vertical = 4f.dp),
                            verticalArrangement = Arrangement.spacedBy(4f.dp)
                        ) {
//                            Info(
//                                name = stringResource(R.string.release_date),
//                                content = releaseDate.formatDate()
//                            ) Release Date Completely incorrect
                            if (boxOfficeRevenue.isNotBlank()) Info(
                                name = stringResource(R.string.box_office_revenue),
                                content = boxOfficeRevenue
                            )
                            if (budget.isNotBlank()) {
                                Info(
                                    name = stringResource(R.string.budget),
                                    content = budget
                                )
                            }
                            if (runtime.isNotBlank()) {
                                Info(
                                    name = stringResource(R.string.runtime),
                                    content = runtime
                                )
                            }
                            if (rating.isNotBlank()) {
                                Info(
                                    name = stringResource(R.string.rating),
                                    content = rating
                                )
                            }
                            if (totalRevenue.isNotBlank()) {
                                Info(
                                    name = stringResource(R.string.total_revenue),
                                    content = totalRevenue
                                )
                            }
                            if (publishers.isNotEmpty()) {

                                Row(Modifier.fillMaxWidth()) {
                                    Text(
                                        "${stringResource(R.string.publisher)}: ",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    publishers.forEach { publisher ->
                                        Text(
                                            publisher.name,
                                            style = MaterialTheme.typography.titleLarge,
                                            modifier = Modifier.clickable { onItemClicked(publisher.apiDetailUrl) })
                                    }
                                }
                            }
                        }
                    }

                    if (charactersId.isNotEmpty()) {
                        panelSeparator()

                        charactersPanel(
                            CommonString.characters,
                            characters,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (teamsId.isNotEmpty()) {
                        panelSeparator()

                        teamsPanel(
                            teams,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (description.isNotBlank()) {
                        webViewPanel(
                            webViewContent,
                            ::expandedProviderCallback,
                            ::onExpand,
                        )
                    } else if (objectsId.isNotEmpty()) {
                        panelSeparator()
                    }

                    if (objectsId.isNotEmpty()) {
                        objectsPanel(
                            objects,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )

                        panelSeparator()
                    }

                    if (locationsId.isNotEmpty()) {
                        locationsPanel(
                            locations,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )

                        panelSeparator()
                    }

                    if (writers.isNotEmpty()) {
                        panel {
                            DetailsFlow(
                                name = stringResource(R.string.writers), items = writers
                            ) { person ->
                                Text(person.name,
                                    Modifier
                                        .clickable { onItemClicked(person.apiDetailUrl) }
                                        .padding(horizontal = 16f.dp),
                                    style = MaterialTheme.typography.titleLarge)
                            }
                        }

                        panelSeparator()
                    }

                    if (producers.isNotEmpty()) {
                        panel {
                            DetailsFlow(
                                name = stringResource(R.string.producers), items = producers
                            ) { person ->
                                Text(person.name,
                                    Modifier
                                        .clickable { onItemClicked(person.apiDetailUrl) }
                                        .padding(horizontal = 16f.dp),
                                    style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}