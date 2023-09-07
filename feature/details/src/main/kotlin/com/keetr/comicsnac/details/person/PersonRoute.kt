package com.keetr.comicsnac.details.person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.ApiBaseUrl
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute


private object PersonRoute : DetailsNavigationRoute("person", "4040") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.personRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = PersonRoute.route,
    deepLinks = PersonRoute.deepLinks
) {
    PersonRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun PersonRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PersonViewModel = hiltViewModel()
) {
    PersonDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        characters = viewModel.characters.collectAsLazyPagingItems(),
        volumes = viewModel.volumes.collectAsLazyPagingItems()
    )
}