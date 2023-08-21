package com.keetr.comicsnac.details.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.keetr.comicsnac.details.InDevelopment
import com.keetr.comicsnac.model.NavigationRoute

const val Arg = "guid"

private object CharacterRoute : NavigationRoute("character/%s/") {
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
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        enemiesUiState = InDevelopment,
        friendsUiState = InDevelopment,
        moviesUiState = InDevelopment,
        teamsUiState = InDevelopment,
        teamEnemiesUiState = InDevelopment,
        teamFriendsUiState = InDevelopment,
        volumeUiState = InDevelopment
    )
}