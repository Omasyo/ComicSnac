package com.keetr.comicsnac.categories.episode

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.categories.CategoryScreen
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.cards.WideCard

private object EpisodeRoute : NavigationRoute("episodes")

internal fun NavGraphBuilder.episodeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(EpisodeRoute.route) {
    EpisodeRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToEpisodes(navOptions: NavOptions? = null) =
    navigate(EpisodeRoute.route, navOptions)

@Composable
internal fun EpisodeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: EpisodeViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.episodes),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        minGridWidth = 160f.dp,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { episode ->
            WideCard(
                name = episode.name,
                description = episode.deck,
                onClick = { onItemClicked(episode.apiDetailUrl) },
                imageUrl = episode.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.episode_image_desc, episode.seriesName
                )
            )
        }
    ) { episode ->
        PlainCard(
            modifier = Modifier.aspectRatio(4f / 3f),
            name = episode.name,
            imageUrl = episode.imageUrl,
            contentDescription = stringResource(
                R.string.episode_image_desc, episode.seriesName
            ),
            onClick = { onItemClicked(episode.apiDetailUrl) }
        )
    }
}