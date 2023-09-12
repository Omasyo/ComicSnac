package com.keetr.comicsnac.categories.`object`

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

private object ObjectRoute : NavigationRoute("objects")

internal fun NavGraphBuilder.objectRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(ObjectRoute.route) {
    ObjectRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToObjects(navOptions: NavOptions? = null) =
    navigate(ObjectRoute.route, navOptions)

@Composable
internal fun ObjectRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: ObjectViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.objects),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { objectItem ->
            WideCard(
                name = objectItem.name,
                description = objectItem.deck,
                onClick = { onItemClicked(objectItem.apiDetailUrl) },
                imageUrl = objectItem.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.object_image_desc, objectItem.name
                )
            )
        }
    ) { objectItem ->
        ComicCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = objectItem.name,
            imageUrl = objectItem.imageUrl,
            contentDescription = stringResource(
                R.string.object_image_desc, objectItem.name
            ),
            onClick = { onItemClicked(objectItem.apiDetailUrl) }
        )
    }
}