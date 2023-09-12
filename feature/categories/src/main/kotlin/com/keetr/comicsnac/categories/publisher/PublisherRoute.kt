package com.keetr.comicsnac.categories.publisher

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

private object PublisherRoute : NavigationRoute("publishers")

internal fun NavGraphBuilder.publisherRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(PublisherRoute.route) {
    PublisherRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToPublishers(navOptions: NavOptions? = null) =
    navigate(PublisherRoute.route, navOptions)

@Composable
internal fun PublisherRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PublisherViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.publishers),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { publisher ->
            WideCard(
                name = publisher.name,
                description = publisher.deck,
                onClick = { onItemClicked(publisher.apiDetailUrl) },
                imageUrl = publisher.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.publisher_image_desc, publisher.name
                )
            )
        }
    ) { publisher ->
        PlainCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = publisher.name,
            imageUrl = publisher.imageUrl,
            contentDescription = stringResource(
                R.string.publisher_image_desc, publisher.name
            ),
            onClick = { onItemClicked(publisher.apiDetailUrl) }
        )
    }
}