package com.keetr.comicsnac.categories.power

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.ui.R

private object PowerRoute : NavigationRoute("powers")

internal fun NavGraphBuilder.powerRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(PowerRoute.route) {
    PowerRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToPowers(navOptions: NavOptions? = null) =
    navigate(PowerRoute.route, navOptions)

@Composable
internal fun PowerRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PowerViewModel = hiltViewModel()
) {
    PowerScreen(
        modifier = modifier,
        title = stringResource(R.string.powers),
        onBackPressed = onBackPressed,
        onItemClicked = onItemClicked,
        powers = viewModel.items.collectAsLazyPagingItems(),
    )
}