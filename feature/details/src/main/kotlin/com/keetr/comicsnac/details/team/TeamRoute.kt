package com.keetr.comicsnac.details.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute
import com.keetr.comicsnac.details.detailsComposable

private object TeamRoute : DetailsNavigationRoute("team", "4060") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.teamRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = detailsComposable(
    route = TeamRoute.route,
    deepLinks = TeamRoute.deepLinks
) {
    TeamRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun TeamRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: TeamViewModel = hiltViewModel()
) {
    TeamDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        characterFriends = viewModel.characterFriends.collectAsLazyPagingItems(),
        characterEnemies = viewModel.characterEnemies.collectAsLazyPagingItems(),
        characters = viewModel.characters.collectAsLazyPagingItems(),
        movies = viewModel.movies.collectAsLazyPagingItems(),
        volumes = viewModel.volumes.collectAsLazyPagingItems()
    )
}