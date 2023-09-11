package com.keetr.comicsnac.categories.series

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
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.cards.WideCard

private object SeriesRoute : NavigationRoute("series")

internal fun NavGraphBuilder.seriesRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(SeriesRoute.route) {
    SeriesRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToSeries(navOptions: NavOptions? = null) =
    navigate(SeriesRoute.route, navOptions)

@Composable
internal fun SeriesRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: SeriesViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.series),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { series ->
            WideCard(
                name = series.name,
                description = series.deck,
                onClick = { onItemClicked(series.apiDetailUrl) },
                imageUrl = series.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.series_image_desc, series.name
                )
            )
        }
    ) { series ->
        PlainCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = series.name,
            imageUrl = series.imageUrl,
            contentDescription = stringResource(
                R.string.series_image_desc, series.name
            ),
            onClick = { onItemClicked(series.apiDetailUrl) }
        )
    }
}