package com.keetr.comicsnac.details.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute

private object LocationRoute : DetailsNavigationRoute("location", "4020") {
    override val requiredArguments: List<String> = listOf(Arg)

    override val deepLinks: List<NavDeepLink>
        get() = super.deepLinks + navDeepLink {
            uriPattern = webDeepLinkPattern.replace("4020", "34")
        }
}

fun NavGraphBuilder.locationRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = composable(
    route = LocationRoute.route,
    deepLinks = LocationRoute.deepLinks
) {
    LocationRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun LocationRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
) {
    LocationDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value

    )
}