package com.keetr.comicsnac.details.publisher

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.Domain
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.PublisherDetailsUiState
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.VolumeDetailsUiState
import com.keetr.comicsnac.details.components.DetailsErrorPlaceholder
import com.keetr.comicsnac.details.components.DetailsLoadingPlaceholder
import com.keetr.comicsnac.details.components.DetailsScreen
import com.keetr.comicsnac.details.components.Image
import com.keetr.comicsnac.details.components.Info
import com.keetr.comicsnac.details.components.panels.charactersPanel
import com.keetr.comicsnac.details.components.panels.issuesPanel
import com.keetr.comicsnac.details.components.panels.volumesPanel
import com.keetr.comicsnac.details.components.panels.webViewPanel
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.components.webview.rememberAnnotatedString
import kotlinx.coroutines.launch
import com.keetr.comicsnac.ui.R.string as CommonString

@Composable
internal fun PublisherDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: PublisherDetailsUiState,
    characters: LazyPagingItems<Character>,
    volumes: LazyPagingItems<Volume>
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

                val annotatedString = rememberAnnotatedString(description, Domain)

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
                            if (aliases.isNotEmpty()) {
                                Info(
                                    name = stringResource(CommonString.aliases),
                                    content = aliases.joinToString(", ")
                                )
                            }
                            Info(
                                name = stringResource(R.string.location),
                                content = location
                            )
                        }
                    }

                    panelSeparator()

                    volumesPanel(
                        volumes,
                        ::expandedProviderCallback,
                        ::onExpand,
                        onItemClicked
                    )

                    panelSeparator()

                    charactersPanel(
                        CommonString.characters,
                        characters,
                        ::expandedProviderCallback,
                        ::onExpand,
                        onItemClicked
                    )

                    if (description.isNotBlank()) {
                        webViewPanel(
                            annotatedString,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }
                }
            }
        }
    }
}