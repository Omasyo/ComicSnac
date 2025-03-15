package com.keetr.comicsnac.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.home.fake.Characters
import com.keetr.comicsnac.home.fake.Issues
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.YetAnotherScheme

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onSearchClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onMoreCategoriesClicked: () -> Unit,
    onCharacterCategoryClicked: () -> Unit,
    onVolumeCategoryClicked: () -> Unit,
    onMovieCategoryClicked: () -> Unit,
    onSeriesCategoryClicked: () -> Unit,
    issuesUiState: IssuesUiState,
    charactersUiState: CharactersUiState,
    volumesUiState: VolumesUiState,
    moviesUiState: MoviesUiState,
    seriesUiState: SeriesUiState,
    publishersUiState: PublishersUiState
) {
    Scaffold(modifier) { padding ->
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            PanelList(
                modifier = Modifier.testTag("home_list"),
                contentPadding = padding
            ) {
                panel {
                    Row(
                        modifier = Modifier
                            .padding(16f.dp, 8f.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Comic Snac",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Box(
                            Modifier
                                .size(48f.dp)
                                .clip(CircleShape)
                                .clickable { onSearchClicked() }
                                .background(MaterialTheme.colorScheme.tertiary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                AppIcons.Search,
                                contentDescription = stringResource(R.string.search_button_desc),
                                modifier = Modifier.testTag("search_button")
                            )
                        }

                    }
                }
                panelSeparator()

                panel {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            stringResource(R.string.new_issues),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 4f.dp)
                        )
                        AnimatedContent(
                            targetState = issuesUiState,
                            modifier = Modifier
                                .animateContentSize(),
                            label = "New Issues Carousel"
                        ) { uiState ->
                            when (uiState) {
                                is Error -> ErrorPlaceholder(
                                    modifier = Modifier
                                        .height(336f.dp)
                                        .fillMaxWidth(),
                                    onRetry = uiState.refresh
                                )

                                Loading -> LoadingPlaceholder(
                                    Modifier
                                        .height(336f.dp)
                                        .fillMaxWidth()
                                )

                                is Success -> {
                                    IssueCarousel(
                                        modifier = Modifier.testTag("issues_carousel"),
                                        issues = uiState.contents,
                                        onIssueClick = onItemClicked
                                    )
                                }
                            }
                        }
                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.characters),
                        key = { it.id },
                        uiState = charactersUiState,
                        onExpand = onCharacterCategoryClicked
                    ) { character ->
                        ComicCard(modifier = Modifier
                            .size(136f.dp, 224.dp)
                            .padding(horizontal = 4f.dp),
                            name = character.name,
                            imageUrl = character.imageUrl,
                            contentDescription = stringResource(
                                R.string.character_image_desc, character.name
                            ),
                            onClick = { onItemClicked(character.apiDetailUrl) })
                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.popular_volumes),
                        uiState = volumesUiState,
                        key = { it.id },
                        onExpand = onVolumeCategoryClicked
                    ) { volume ->
                        PlainCard(modifier = Modifier
                            .size(136f.dp, 224.dp)
                            .padding(horizontal = 4f.dp),
                            name = volume.name,
                            imageUrl = volume.imageUrl,
                            contentDescription = stringResource(
                                R.string.volume_image_desc, volume.name
                            ),
                            onClick = { onItemClicked(volume.apiDetailUrl) })
                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.movies),
                        uiState = moviesUiState,
                        key = { it.id },
                        onExpand = onMovieCategoryClicked
                    ) { movie ->
                        PlainCard(modifier = Modifier
                            .size(136f.dp, 224.dp)
                            .padding(horizontal = 4f.dp),
                            name = movie.name,
                            imageUrl = movie.imageUrl,
                            contentDescription = stringResource(
                                R.string.movie_image_desc, movie.name
                            ),
                            onClick = { onItemClicked(movie.apiDetailUrl) })
                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.series),
                        uiState = seriesUiState,
                        key = { it.id },
                        onExpand = onSeriesCategoryClicked
                    ) { series ->
                        PlainCard(modifier = Modifier
                            .size(136f.dp, 224.dp)
                            .padding(horizontal = 4f.dp),
                            name = series.name,
                            imageUrl = series.imageUrl,
                            contentDescription = stringResource(
                                R.string.movie_image_desc, series.name
                            ),
                            onClick = { onItemClicked(series.apiDetailUrl) })
                    }
                }

                panelSeparator()

                panel {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            stringResource(com.keetr.comicsnac.home.R.string.popular_publishers),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 4f.dp)
                        )
                        AnimatedContent(
                            targetState = publishersUiState,
                            modifier = Modifier.animateContentSize(),
                            label = "Publishers Carousel"
                        ) { uiState ->
                            when (uiState) {
                                is Error -> ErrorPlaceholder(
                                    modifier = Modifier
                                        .height(336f.dp)
                                        .fillMaxWidth(),
                                    onRetry = uiState.refresh
                                )


                                Loading -> LoadingPlaceholder(
                                    Modifier
                                        .height(336f.dp)
                                        .fillMaxWidth()
                                )

                                is Success -> {
                                    PublisherCarousel(
                                        Modifier.testTag("publisher_carousel"),
                                        publishers = uiState.contents, onIssueClick = onItemClicked
                                    )
                                }
                            }
                        }
                    }
                }

                panelSeparator()

                panel {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24f.dp, top = 16f.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick = onSettingsClicked,
                            modifier = Modifier
                                .padding(12f.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(AppIcons.Settings, null, Modifier.size(32f.dp))
                        }
                        Text(
                            stringResource(com.keetr.comicsnac.home.R.string.more_categories),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
//                                .align(Alignment.CenterEnd)
                                .clickable { onMoreCategoriesClicked() }
                                .background(MaterialTheme.colorScheme.onSurface)
                                .padding(horizontal = 16f.dp, vertical = 4f.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme(YetAnotherScheme) {
        HomeScreen(
            onItemClicked = {},
            onSearchClicked = {},
            onMoreCategoriesClicked = { },
            onSettingsClicked = {},
            onCharacterCategoryClicked = { },
            onVolumeCategoryClicked = { },
            onMovieCategoryClicked = { },
            onSeriesCategoryClicked = { },
            issuesUiState = Success(Issues),
            charactersUiState = Success(Characters),
            volumesUiState = Loading,
            moviesUiState = Error(RepositoryResponse.TimeoutError, {}),
            seriesUiState = Loading,
            publishersUiState = Loading

        )
    }
}