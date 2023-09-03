package com.keetr.comicsnac.details.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute
import com.keetr.comicsnac.details.detailsComposable

private object CharacterRoute : DetailsNavigationRoute("character", "4005") {
    override val requiredArguments: List<String> = listOf(Arg)

    override val deepLinks: List<NavDeepLink>
        get() = super.deepLinks + navDeepLink {
            uriPattern = apiDeepLinkPattern.replace("4005", "29")
        }
}

fun NavGraphBuilder.characterRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = detailsComposable(
    route = CharacterRoute.route,
    deepLinks = CharacterRoute.deepLinks
) {
    CharacterRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun CharacterRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    CharacterDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        enemies = viewModel.enemies.collectAsLazyPagingItems(),
        friends = viewModel.friends.collectAsLazyPagingItems(),
        movies = viewModel.movies.collectAsLazyPagingItems(),
        teams = viewModel.teams.collectAsLazyPagingItems(),
        teamEnemies = viewModel.teamEnemies.collectAsLazyPagingItems(),
        teamFriends = viewModel.teamFriends.collectAsLazyPagingItems(),
        volumes = viewModel.volumes.collectAsLazyPagingItems()
    )
}