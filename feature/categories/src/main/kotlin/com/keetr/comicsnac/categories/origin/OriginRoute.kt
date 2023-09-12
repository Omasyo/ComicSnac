package com.keetr.comicsnac.categories.origin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.categories.CategoryScreen
import com.keetr.comicsnac.model.LayoutType
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.cards.WideCard

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