package com.keetr.comicsnac.details.storyarc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute

private object StoryArcRoute : DetailsNavigationRoute("story_arc", "4045") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.storyArcRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = StoryArcRoute.route,
    deepLinks = StoryArcRoute.deepLinks
) {
    StoryArcRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun StoryArcRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: StoryArcViewModel = hiltViewModel()
) {
    StoryArcDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        issues = viewModel.issues.collectAsLazyPagingItems(),
        episodes = viewModel.episodes.collectAsLazyPagingItems()
    )
}