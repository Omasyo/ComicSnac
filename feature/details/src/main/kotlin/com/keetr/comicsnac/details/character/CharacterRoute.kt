package com.keetr.comicsnac.details.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.model.NavigationRoute

const val Arg = "id"

private object CharacterRoute : NavigationRoute("character/4005-%s/") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.characterRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = CharacterRoute.route,
    deepLinks = listOf(
        navDeepLink {
            uriPattern = "https://comicvine.gamespot.com/api/${CharacterRoute.route}"
        },
        navDeepLink {
            uriPattern = "https://comicvine.gamespot.com/{_}/4005-{$Arg}/"
        }
    )
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