package com.keetr.comicsnac.details.concept

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

private object ConceptRoute : DetailsNavigationRoute("concept", "4015") {
    override val requiredArguments: List<String> = listOf(Arg)

    override val deepLinks: List<NavDeepLink>
        get() = super.deepLinks + navDeepLink {
            uriPattern = webDeepLinkPattern.replace("4015", "12")
        }
}

fun NavGraphBuilder.conceptRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = ConceptRoute.route,
    deepLinks = ConceptRoute.deepLinks
) {
    ConceptRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun ConceptRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: ConceptViewModel = hiltViewModel()
) {
    ConceptDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value,
        issues = viewModel.issues.collectAsLazyPagingItems(),
        volumes = viewModel.volumes.collectAsLazyPagingItems()
    )
}