package com.keetr.comicsnac.categories.locations

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.categories.CategoryScreen
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.WideCard

private object LocationRoute : NavigationRoute("locations")

internal fun NavGraphBuilder.locationRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(LocationRoute.route) {
    LocationRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToLocations(navOptions: NavOptions? = null) =
    navigate(LocationRoute.route, navOptions)

@Composable
internal fun LocationRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.locations),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { location ->
            WideCard(
                name = location.name,
                description = location.deck,
                onClick = { onItemClicked(location.apiDetailUrl) },
                imageUrl = location.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.location_image_desc, location.name
                )
            )
        }
    ) { location ->
        ComicCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = location.name,
            imageUrl = location.imageUrl,
            contentDescription = stringResource(
                R.string.location_image_desc, location.name
            ),
            onClick = { onItemClicked(location.apiDetailUrl) }
        )
    }
}