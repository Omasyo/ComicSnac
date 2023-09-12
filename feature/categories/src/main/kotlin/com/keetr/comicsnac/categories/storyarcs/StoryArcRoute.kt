package com.keetr.comicsnac.categories.storyarcs

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

private object StoryArcRoute : NavigationRoute("storyArcs")

internal fun NavGraphBuilder.storyArcRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(StoryArcRoute.route) {
    StoryArcRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToStoryArcs(navOptions: NavOptions? = null) =
    navigate(StoryArcRoute.route, navOptions)

@Composable
internal fun StoryArcRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: StoryArcViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.story_arcs),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { storyArc ->
            WideCard(
                name = storyArc.name,
                description = storyArc.deck,
                onClick = { onItemClicked(storyArc.apiDetailUrl) },
                imageUrl = storyArc.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.story_arc_image_desc, storyArc.name
                )
            )
        }
    ) { storyArc ->
        ComicCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = storyArc.name,
            imageUrl = storyArc.imageUrl,
            contentDescription = stringResource(
                R.string.story_arc_image_desc, storyArc.name
            ),
            onClick = { onItemClicked(storyArc.apiDetailUrl) }
        )
    }
}