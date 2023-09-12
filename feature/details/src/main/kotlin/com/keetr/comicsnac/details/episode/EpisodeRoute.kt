package com.keetr.comicsnac.details.episode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute

private object EpisodeRoute : DetailsNavigationRoute("episode", "4070") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.episodeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = EpisodeRoute.route,
    deepLinks = EpisodeRoute.deepLinks
) {
    EpisodeRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun EpisodeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: EpisodeViewModel = hiltViewModel()
) {
    EpisodeDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        characters = viewModel.characters.collectAsLazyPagingItems(),
        locations = viewModel.locations.collectAsLazyPagingItems(),
        objects = viewModel.objects.collectAsLazyPagingItems(),
        teams = viewModel.teams.collectAsLazyPagingItems(),

        )
}