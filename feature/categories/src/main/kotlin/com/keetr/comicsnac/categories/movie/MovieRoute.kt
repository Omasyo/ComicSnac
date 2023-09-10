package com.keetr.comicsnac.categories.movie

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

private object MovieRoute : NavigationRoute("movies")

internal fun NavGraphBuilder.movieRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(MovieRoute.route) {
    MovieRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToMovies(navOptions: NavOptions? = null) =
    navigate(MovieRoute.route, navOptions)

@Composable
internal fun MovieRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: MovieViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.movies),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { movie ->
            WideCard(
                name = movie.name,
                description = movie.deck,
                onClick = { onItemClicked(movie.apiDetailUrl) },
                imageUrl = movie.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.movie_image_desc, movie.name
                )
            )
        }
    ) { movie ->
        PlainCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = movie.name,
            imageUrl = movie.imageUrl,
            contentDescription = stringResource(
                R.string.movie_image_desc, movie.name
            ),
            onClick = { onItemClicked(movie.apiDetailUrl) }
        )
    }
}