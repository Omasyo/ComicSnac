package com.keetr.comicsnac.details.`object`

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.details.Arg
import com.keetr.comicsnac.details.DetailsNavigationRoute
import com.keetr.comicsnac.details.detailsComposable

private object ObjectRoute : DetailsNavigationRoute("object", "4020") {
    override val requiredArguments: List<String> = listOf(Arg)
}

fun NavGraphBuilder.objectRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
) = detailsComposable(
    route = ObjectRoute.route,
    deepLinks = ObjectRoute.deepLinks
) {
    ObjectRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun ObjectRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: ObjectViewModel = hiltViewModel()
) {
    ObjectDetailsScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed,
        detailsUiState = viewModel.detailsUiState.collectAsState().value
    )
}