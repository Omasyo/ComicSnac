package com.keetr.comicsnac.details.team

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

private object TeamRoute : DetailsNavigationRoute("team", "4060") {
    override val requiredArguments: List<String> = listOf(Arg)

    override val deepLinks: List<NavDeepLink>
        get() = super.deepLinks + navDeepLink {
            uriPattern = webDeepLinkPattern.replace("4060", "65")
        }
}

fun NavGraphBuilder.teamRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
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