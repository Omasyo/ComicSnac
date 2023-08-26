package com.keetr.comicsnac.details.issue

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.Domain
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.IssueDetailsUiState
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
import com.keetr.comicsnac.details.components.panels.teamsPanel
import com.keetr.comicsnac.details.components.panels.webViewPanel
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.details.formatDate
import com.keetr.comicsnac.model.location.LocationBasic
import com.keetr.comicsnac.model.`object`.ObjectBasic
import com.keetr.comicsnac.model.storyarc.StoryArcBasic
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.components.webview.rememberAnnotatedString
import kotlinx.coroutines.launch
import com.keetr.comicsnac.ui.R.string as CommonString

@Composable
internal fun IssueDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: IssueDetailsUiState,
    characters: LazyPagingItems<Character>,
    locations: LazyPagingItems<LocationBasic>, //TODO
    objects: LazyPagingItems<ObjectBasic>,
    storyArcs: LazyPagingItems<StoryArcBasic>,
    teams: LazyPagingItems<Team>,
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

            var imageExpanded by remember {
                mutableStateOf(false)
            }

            val state = rememberLazyListState()
            var expandedIndex by remember {
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

                val annotatedString = rememberAnnotatedString(description, Domain)

                DetailsScreen(
                    modifier = modifier,
                    images = (listOf(imageUrl) + associatedImagesUrl).map {
                        Image(
                            it, stringResource(CommonString.issue_image_desc)
                        )
                    },
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

                    ) {
                    panel {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16f.dp, vertical = 8f.dp),
                            verticalArrangement = Arrangement.spacedBy(4f.dp),
                        ) {
                            Text(
                                "${volume.name} #$issueNumber",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.clickable { onItemClicked(volume.apiDetailUrl) }
                            )
                            Text(name, style = MaterialTheme.typography.headlineSmall)
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
                            coverDate?.let {
                                Info(
                                    name = stringResource(R.string.cover_date),
                                    content = it.formatDate()
                                )
                            }
                            storeDate?.let {
                                Info(
                                    name = stringResource(R.string.store_date),
                                    content = it.formatDate()
                                )
                            }
                        }
                    }

                    if (credits.isNotEmpty()) {
                        panelSeparator()

                        panel {
                            DetailsFlow(
                                name = stringResource(R.string.credits), items = credits
                            ) { credit ->
                                Column(
                                    Modifier.padding(horizontal = 16f.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(credit.name,
                                        Modifier
                                            .clickable { onItemClicked(credit.apiDetailUrl) }
                                            .padding(horizontal = 16f.dp),
                                        style = MaterialTheme.typography.titleLarge)
                                    Text(
                                        credit.role,
                                        Modifier
                                            .padding(horizontal = 8f.dp),
                                        style = MaterialTheme.typography.titleMedium
                                    )
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
                            annotatedString,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    } else if (concepts.isNotEmpty()) {
                        panelSeparator()
                    }

                    if (concepts.isNotEmpty()) {

                        panel {
                            DetailsFlow(
                                name = stringResource(CommonString.concepts), items = concepts
                            ) { concept ->
                                Text(concept.name,
                                    Modifier
                                        .clickable { onItemClicked(concept.apiDetailUrl) }
                                        .padding(horizontal = 8f.dp),
                                    style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}