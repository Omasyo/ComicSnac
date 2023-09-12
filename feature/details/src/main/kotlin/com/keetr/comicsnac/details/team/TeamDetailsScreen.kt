package com.keetr.comicsnac.details.team

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
import com.keetr.comicsnac.details.components.DetailsLoadingPlaceholder
import com.keetr.comicsnac.details.components.DetailsScreen
import com.keetr.comicsnac.details.components.Image
import com.keetr.comicsnac.details.components.Info
import com.keetr.comicsnac.details.components.panels.charactersPanel
import com.keetr.comicsnac.details.components.panels.enemiesPanel
import com.keetr.comicsnac.details.components.panels.friendsPanel
import com.keetr.comicsnac.details.components.panels.moviesPanel
import com.keetr.comicsnac.details.components.panels.volumesPanel
import com.keetr.comicsnac.details.components.panels.webViewPanel
import com.keetr.comicsnac.details.components.shareUrl
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.team.TeamDetails
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.components.webview.rememberAnnotatedString
import kotlinx.coroutines.launch
import com.keetr.comicsnac.ui.R.string as CommonString

@Composable
internal fun TeamDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: DetailsUiState<TeamDetails>,
    characterFriends: LazyPagingItems<Character>,
    characterEnemies: LazyPagingItems<Character>,
    characters: LazyPagingItems<Character>,
    movies: LazyPagingItems<Movie>,
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
                val context = LocalContext.current

                DetailsScreen(
                    modifier = modifier,
                    images = listOf(
                        Image(
                            imageUrl, stringResource(CommonString.character_image_desc)
                        ),
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
                            if (aliases.isNotEmpty()) {
                                Info(
                                    name = stringResource(CommonString.aliases),
                                    content = aliases.joinToString(", ")
                                )
                            }
                            Info(
                                name = stringResource(R.string.first_appeared_in_issue),
                                content = firstAppearedInIssue.name.ifBlank { "Unknown name" }
                            ) {
                                onItemClicked(firstAppearedInIssue.apiDetailUrl)
                            }
                            Info(
                                name = stringResource(R.string.no_of_members),
                                content = countOfMembers.toString()
                            )
                            publisher?.let {
                                Info(name = stringResource(R.string.publisher), content = it.name) {
                                    onItemClicked(it.apiDetailUrl)
                                }
                            }
                        }
                    }

                    if (charactersId.isNotEmpty()) {
                        panelSeparator()

                        charactersPanel(
                            R.string.members,
                            characters,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (characterFriendsId.isNotEmpty()) {
                        panelSeparator()

                        friendsPanel(
                            characterFriends,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (characterEnemiesId.isNotEmpty()) {
                        panelSeparator()

                        enemiesPanel(
                            characterEnemies,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (annotatedString.isNotBlank()) {
                        webViewPanel(
                            annotatedString,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    } else if (volumeCreditsId.isNotEmpty()) {
                        panelSeparator()
                    }

                    if (volumeCreditsId.isNotEmpty()) {
                        volumesPanel(
                            volumes,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (moviesId.isNotEmpty()) {
                        panelSeparator()

                        moviesPanel(
                            movies,
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