package com.keetr.comicsnac.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.ui.components.PanelColors
import com.keetr.comicsnac.ui.components.panelList
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onMoreCategoriesClicked: () -> Unit,
    onCharacterCategoryClicked: () -> Unit,
    onVolumeCategoryClicked: () -> Unit,
    onMovieCategoryClicked: () -> Unit,
    onSeriesCategoryClicked: () -> Unit,
    homeUiState: HomeUiState
) {
    val panelColors = with(MaterialTheme.colorScheme) {
        PanelColors(
            strokeColor = outline,
            surface1 = primary,
            surface2 = tertiary,
            surface3 = secondary
        )
    }

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
        LazyColumn {
            panelList(panelColors) {
                panel {
                    Text(
                        "Comic Snac",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16f.dp, 8f.dp)
                    )
                }
                panel {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(360f.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "New Issues",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 4f.dp)
                        )
                        Spacer(Modifier.height(48f.dp))
                        when (val uiState = homeUiState.issuesUiState) {
                            is HomeCategoryUiState.Error -> TODO()
                            HomeCategoryUiState.InDevelopment -> Box(Modifier.fillMaxSize()) {
                                Text(
                                    "In Development",
                                    style = MaterialTheme.typography.headlineLarge,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            HomeCategoryUiState.Loading -> TODO()
                            is HomeCategoryUiState.Success -> {
                                IssueCarousel(issues = uiState.contents)
                            }
                        }
                    }
                }

                panel {
                    CategoryCarousel(
                        name = "Characters",
                        uiState = homeUiState.charactersUiState
                    ) {

                    }
                }

                panel {
                    CategoryCarousel(
                        name = "Popular Volumes",
                        uiState = homeUiState.volumesUiState
                    ) {

                    }
                }

                panel {
                    CategoryCarousel(
                        name = "Movies",
                        uiState = homeUiState.moviesUiState
                    ) {

                    }
                }

                panel {
                    CategoryCarousel(
                        name = "Series",
                        uiState = homeUiState.seriesUiState
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
private fun <T> CategoryCarousel(
    modifier: Modifier = Modifier,
    name: String,
    uiState: HomeCategoryUiState<T>,
    builder: @Composable (item: T) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .height(360f.dp)
    ) {
        Text(
            name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16f.dp, top = 8f.dp)
        )

        when (uiState) {
            is HomeCategoryUiState.Error -> TODO()
            HomeCategoryUiState.InDevelopment -> Box(Modifier.fillMaxSize()) {
                Text(
                    "In Development",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            HomeCategoryUiState.Loading -> TODO()
            is HomeCategoryUiState.Success -> {
                LazyRow {
                    items(uiState.contents) {
                        builder(it)
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
        HomeScreen(
            onItemClicked = {},
            onMoreCategoriesClicked = { },
            onCharacterCategoryClicked = { },
            onVolumeCategoryClicked = { },
            onMovieCategoryClicked = { },
            onSeriesCategoryClicked = { },
            homeUiState = HomeUiState(
                issuesUiState = HomeCategoryUiState.Success(Issues),
                charactersUiState = HomeCategoryUiState.InDevelopment,
                volumesUiState = HomeCategoryUiState.InDevelopment,
                moviesUiState = HomeCategoryUiState.InDevelopment,
                seriesUiState = HomeCategoryUiState.InDevelopment,
                publishersUiState = HomeCategoryUiState.InDevelopment

            )
        )
    }
}