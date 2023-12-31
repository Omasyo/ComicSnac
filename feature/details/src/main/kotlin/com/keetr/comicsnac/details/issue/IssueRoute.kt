package com.keetr.comicsnac.details.issue

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


private object IssueRoute : DetailsNavigationRoute("issue", "4000") {
    override val requiredArguments: List<String> = listOf(Arg)

    override val deepLinks: List<NavDeepLink>
        get() = super.deepLinks + navDeepLink {
            uriPattern = "${ApiBaseUrl}first_appeared_in_issue/4000-{$Arg}/"
        }
}

fun NavGraphBuilder.issueRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = IssueRoute.route,
    deepLinks = IssueRoute.deepLinks
) {
    IssueRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun IssueRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: IssueViewModel = hiltViewModel()
) {
    IssueDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        characters = viewModel.characters.collectAsLazyPagingItems(),
        locations = viewModel.locations.collectAsLazyPagingItems(),
        objects = viewModel.objects.collectAsLazyPagingItems(),
        storyArcs = viewModel.storyArcs.collectAsLazyPagingItems(),
        teams = viewModel.teams.collectAsLazyPagingItems()
    )
}