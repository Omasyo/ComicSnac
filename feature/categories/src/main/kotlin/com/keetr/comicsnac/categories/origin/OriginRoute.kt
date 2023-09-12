package com.keetr.comicsnac.categories.origin

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

private object OriginRoute : NavigationRoute("origins")

internal fun NavGraphBuilder.originRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(OriginRoute.route) {
    OriginRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToOrigins(navOptions: NavOptions? = null) =
    navigate(OriginRoute.route, navOptions)

@Composable
internal fun OriginRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: OriginViewModel = hiltViewModel()
) {
    OriginScreen(
        modifier = modifier,
        title = stringResource(R.string.origins),
        onBackPressed = onBackPressed,
        onItemClicked = onItemClicked,
        origins = viewModel.items.collectAsLazyPagingItems(),
    )
}