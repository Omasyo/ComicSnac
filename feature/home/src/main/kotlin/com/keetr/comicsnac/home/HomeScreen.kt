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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.keetr.comicsnac.ui.components.placeholders.InDevelopmentPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AnotherScheme
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.DarkKnightScheme
import com.keetr.comicsnac.ui.theme.DefaultScheme
import com.keetr.comicsnac.ui.theme.DoggyBagsScheme
import com.keetr.comicsnac.ui.theme.IronManScheme
import com.keetr.comicsnac.ui.theme.LightScheme
import com.keetr.comicsnac.ui.theme.SpawnScheme
import com.keetr.comicsnac.ui.theme.YetAnotherScheme

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onSearchClicked: () -> Unit,
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
            PanelList(contentPadding = padding) {
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
                            style = MaterialTheme.typography.headlineLarge,
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
                                contentDescription = stringResource(R.string.share_button_desc)
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
                            modifier = Modifier.animateContentSize(),
                            label = "New Issues Carousel"
                        ) { uiState ->
                            when (uiState) {
                                is Error -> ErrorPlaceholder(Modifier.height(336f.dp))
                                InDevelopment -> InDevelopmentPlaceholder(Modifier.height(336f.dp))
                                Loading -> LoadingPlaceholder(Modifier.height(336f.dp))
                                is Success -> {
                                    IssueCarousel(
                                        issues = uiState.contents, onIssueClick = onItemClicked
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
                    ) {

                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.series),
                        uiState = seriesUiState,
                        key = { it.id },
                        onExpand = onSeriesCategoryClicked
                    ) {

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
                                is Error -> ErrorPlaceholder(Modifier.height(336f.dp))
                                InDevelopment -> InDevelopmentPlaceholder(Modifier.height(336f.dp))
                                Loading -> LoadingPlaceholder(Modifier.height(336f.dp))
                                is Success -> {
                                    PublisherCarousel(
                                        issues = uiState.contents, onIssueClick = onItemClicked
                                    )
                                }
                            }
                        }
                    }
                }

                panelSeparator()

                panel {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24f.dp)
                    ) {
                        Text(
                            stringResource(com.keetr.comicsnac.home.R.string.more_categories),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(top = 16f.dp)
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
            onCharacterCategoryClicked = { },
            onVolumeCategoryClicked = { },
            onMovieCategoryClicked = { },
            onSeriesCategoryClicked = { },
            issuesUiState = Success(Issues),
            charactersUiState = Success(Characters),
            volumesUiState = InDevelopment,
            moviesUiState = Error(RepositoryResponse.TimeoutError),
            seriesUiState = InDevelopment,
            publishersUiState = InDevelopment

        )
    }
}