package com.keetr.comicsnac.details.publisher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute
import com.keetr.comicsnac.details.detailsComposable

private object PublisherRoute : DetailsNavigationRoute("publisher", "4010") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.publisherRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = detailsComposable(
    route = PublisherRoute.route,
    deepLinks = PublisherRoute.deepLinks
) {
    PublisherRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun PublisherRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PublisherViewModel = hiltViewModel()
) {
    PublisherDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        characters = viewModel.characters.collectAsLazyPagingItems(),
        volumes = viewModel.volumes.collectAsLazyPagingItems()
    )
}