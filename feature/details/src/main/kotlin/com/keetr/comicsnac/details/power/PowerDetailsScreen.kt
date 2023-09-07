package com.keetr.comicsnac.details.power

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.details.DetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.IssueDetailsUiState
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.PowerDetailsUiState
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.components.ImageCarousel
import com.keetr.comicsnac.details.components.PlaceholderDesc
import com.keetr.comicsnac.details.components.PlaceholderName
import com.keetr.comicsnac.details.components.panels.charactersPanel
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.power.PowerDetails
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.lazylist.ComicListSeparator
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PowerDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: PowerDetailsUiState,
    characters: LazyPagingItems<Character>
) {
    Scaffold(
        modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {

            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
                    .padding(top = 8f.dp)
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(AppIcons.ArrowBack, stringResource(R.string.back_button_desc))
                }
            }
            AnimatedContent(
                targetState = detailsUiState, label = "power_details_header",
                modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
                    .padding(horizontal = 16f.dp, vertical = 8f.dp),
            ) { state ->
                when (state) {
                    is Error -> HeaderErrorPlaceholder()
                    Loading -> HeaderLoadingPlaceholder()
                    is Success -> with(state.content) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4f.dp),
                        ) {
                            Text(name, style = MaterialTheme.typography.headlineMedium)
                            Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            with(MaterialTheme.colorScheme) {
                ComicListSeparator(
                    upperColor = secondary,
                    lowerColor = primary,
                    strokeColor = outline,
                    flipped = false
                )
            }

            AnimatedContent(
                targetState = characters.loadState.refresh,
                label = "characters_content"
            ) { refreshState ->
                when (refreshState) {
                    is LoadState.Error -> ErrorPlaceholder(
                        Modifier.fillMaxSize(),
                        onRetry = if (detailsUiState is Success) {
                            { characters.retry() }
                        } else null
                    )

                    LoadState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                    is LoadState.NotLoading -> Column(
                        modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(R.string.characters),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(
                                start = 16f.dp,
                                top = 8f.dp,
                                bottom = 16f.dp
                            )
                        )
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16f.dp),
                            columns = GridCells.Adaptive(96f.dp),
                            horizontalArrangement = Arrangement.spacedBy(16f.dp),
                            verticalArrangement = Arrangement.spacedBy(16f.dp),
                            state = rememberLazyGridState()
                        ) {
                            items(characters.itemCount) {
                                with(characters[it]!!) {
                                    ComicCard(
                                        modifier = Modifier.aspectRatio(6f / 11f),
                                        name = name,
                                        imageUrl = imageUrl,
                                        contentDescription = stringResource(
                                            R.string.character_image_desc, name
                                        ),
                                        onClick = { onItemClicked(apiDetailUrl) }
                                    )
                                }
                            }
                            if (characters.loadState.append == LoadState.Loading) {
                                item {
                                    LoadingPlaceholder()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview
@Composable
private fun HeaderLoadingPlaceholder(modifier: Modifier = Modifier) {
    var dotCount by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            dotCount = dotCount.plus(1) % 12
            delay(450)
        }
    }

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(4f.dp),
    ) {
        Text(
            PlaceholderName.replaceRange(dotCount, dotCount, "_"),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            PlaceholderDesc.repeat(2),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

//@Preview
@Composable
private fun HeaderErrorPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(4f.dp),
    ) {
        Text(
            PlaceholderName,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            PlaceholderDesc.repeat(2),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
internal fun Preview() {
    ComicSnacTheme {
        PowerDetailsScreen(
            onItemClicked = {},
            onBackPressed = { /*TODO*/ },
            detailsUiState = Error(RepositoryResponse.InvalidApiKeyError),
            characters = flow {
                emit(PagingData.from(List(100) {
                    Character(
                        apiDetailUrl = "https://www.google.com/#q=odio",
                        deck = "propriae",
                        id = it,
                        imageUrl = "https://search.yahoo.com/search?p=quod",
                        name = "Kerry Cabrera",
                    )
                }))
            }.collectAsLazyPagingItems()
        )
    }
}