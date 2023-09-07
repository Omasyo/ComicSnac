package com.keetr.comicsnac.categories.volume

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
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.cards.WideCard

private object VolumeRoute : NavigationRoute("volumes")

internal fun NavGraphBuilder.volumeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(VolumeRoute.route) {
    VolumeRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToVolumes(navOptions: NavOptions? = null) =
    navigate(VolumeRoute.route, navOptions)

@Composable
internal fun VolumeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: VolumeViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.volumes),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { volume ->
            WideCard(
                name = volume.name,
                description = volume.deck,
                onClick = { onItemClicked(volume.apiDetailUrl) },
                imageUrl = volume.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.volume_image_desc, volume.name
                )
            )
        }
    ) { volume ->
        PlainCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = volume.name,
            imageUrl = volume.imageUrl,
            contentDescription = stringResource(
                R.string.volume_image_desc, volume.name
            ),
            onClick = { onItemClicked(volume.apiDetailUrl) }
        )
    }
}