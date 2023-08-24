package com.keetr.comicsnac.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.home.fake.Characters
import com.keetr.comicsnac.home.fake.Issues
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.InDevelopmentPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onMoreCategoriesClicked: () -> Unit,
    onCharacterCategoryClicked: () -> Unit,
    onVolumeCategoryClicked: () -> Unit,
    onMovieCategoryClicked: () -> Unit,
    onSeriesCategoryClicked: () -> Unit,
    homeUiState: HomeUiState
) {
    Scaffold(modifier) { padding ->
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            PanelList(contentPadding = padding) {
                panel {
                    Text(
                        "Comic Snac",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16f.dp, 8f.dp)
                    )
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
                            targetState = homeUiState.issuesUiState,
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
                        uiState = homeUiState.charactersUiState,
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
                        uiState = homeUiState.volumesUiState,
                        key = { it.id },
                        onExpand = onVolumeCategoryClicked
                    ) {
                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.movies),
                        uiState = homeUiState.moviesUiState,
                        key = { it.id },
                        onExpand = onMovieCategoryClicked
                    ) {

                    }
                }

                panelSeparator()

                panel {
                    CategoryCarousel(
                        name = stringResource(R.string.series),
                        uiState = homeUiState.seriesUiState,
                        key = { it.id },
                        onExpand = onSeriesCategoryClicked
                    ) {

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
                            "More Categories",
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
    ComicSnacTheme {
        CompositionLocalProvider(
        ) {
            HomeScreen(
                onItemClicked = {},
                onMoreCategoriesClicked = { },
                onCharacterCategoryClicked = { },
                onVolumeCategoryClicked = { },
                onMovieCategoryClicked = { },
                onSeriesCategoryClicked = { },
                homeUiState = HomeUiState(
                    issuesUiState = Success(Issues),
                    charactersUiState = Success(Characters),
                    volumesUiState = InDevelopment,
                    moviesUiState = InDevelopment,
                    seriesUiState = InDevelopment,
                    publishersUiState = InDevelopment

                )
            )
        }
    }
}