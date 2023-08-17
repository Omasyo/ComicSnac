package com.keetr.comicsnac.home

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.ui.components.PanelColors
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.panelList
import com.keetr.comicsnac.ui.placeholders.InDevelopmentPlaceholder
import com.keetr.comicsnac.ui.placeholders.LoadingPlaceholder
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

    Scaffold { padding ->
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            LazyColumn(contentPadding = padding) {
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
                                .fillMaxWidth(),
//                                .height(376f.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "New Issues",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 4f.dp)
                            )
                            when (val uiState = homeUiState.issuesUiState) {
                                is Error -> TODO()
                                InDevelopment -> InDevelopmentPlaceholder(Modifier.height(360f.dp))
                                Loading -> LoadingPlaceholder(Modifier.height(360f.dp))

                                is Success -> {
                                    var works = remember {
                                        mutableStateOf(false)
                                    }
                                    if (works.value) Text("Works")
                                    IssueCarousel(issues = uiState.contents) {
                                        works.value = !works.value
                                    }
                                }
                            }
                        }
                    }

                    panel {
                        CategoryCarousel(
                            name = "Characters",
                            key = { it.id },
                            uiState = homeUiState.charactersUiState
                        ) { character ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(144f.dp)
                            ) {
                                ComicCard(Modifier.size(136f.dp, 216f.dp)) {

                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(character.imageUrl)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Poster for Issue smotheing", //Add proper string resource
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Text(
                                    character.name,
                                    modifier = Modifier.padding(horizontal = 8f.dp),
                                    textAlign = TextAlign.Center,
                                    minLines = 2,
                                    maxLines = 2
                                )
                            }
                        }
                    }

                    panel {
                        CategoryCarousel(
                            name = "Popular Volumes",
                            uiState = homeUiState.volumesUiState,
                            key = { /* TODO */ }
                        ) {

                        }
                    }

                    panel {
                        CategoryCarousel(
                            name = "Movies",
                            uiState = homeUiState.moviesUiState,
                            key = { /* TODO */ }
                        ) {

                        }
                    }

                    panel {
                        CategoryCarousel(
                            name = "Series",
                            uiState = homeUiState.seriesUiState,
                            key = { /* TODO */ }
                        ) {

                        }
                    }
                }
            }
        }

    }

}

@Preview(fontScale = 1.0f)
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

val Characters = List(30) {
    Character(
        apiDetailUrl = "http://www.bing.com/search?q=eius",
        deck = "tation",
        id = it,
        imageUrl = "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8759934-jrjrhr.jpg",
        name = "Beatriz Coleman",
        siteDetailUrl = "https://www.google.com/#q=fames"
    )
}