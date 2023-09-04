package com.keetr.comicsnac.categories

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keetr.comicsnac.model.NavigationRoute


private object CategoriesRoute : NavigationRoute("categories")

fun NavController.navigateToCategories() = navigate(CategoriesRoute.route)

fun NavGraphBuilder.categoriesRoute(
    modifier: Modifier = Modifier,
    onCharactersClicked: () -> Unit,
    onConceptsClicked: () -> Unit,
    onEpisodesClicked: () -> Unit,
    onIssuesClicked: () -> Unit,
    onLocationsClicked: () -> Unit,
    onMoviesClicked: () -> Unit,
    onObjectsClicked: () -> Unit,
    onOriginsClicked: () -> Unit,
    onPeopleClicked: () -> Unit,
    onPowersClicked: () -> Unit,
    onPublishersClicked: () -> Unit,
    onSeriesClicked: () -> Unit,
    onStoryArcsClicked: () -> Unit,
    onTeamsClicked: () -> Unit,
    onVolumesClicked: () -> Unit,
    onBackPressed: () -> Unit
) = categoriesComposable(
    "/"
) {
    CategoriesScreen(
        modifier = modifier,
        onCharactersClicked = onCharactersClicked,
        onConceptsClicked = onConceptsClicked,
        onEpisodesClicked = onEpisodesClicked,
        onIssuesClicked = onIssuesClicked,
        onLocationsClicked = onLocationsClicked,
        onMoviesClicked = onMoviesClicked,
        onObjectsClicked = onObjectsClicked,
        onOriginsClicked = onOriginsClicked,
        onPeopleClicked = onPeopleClicked,
        onPowersClicked = onPowersClicked,
        onPublishersClicked = onPublishersClicked,
        onSeriesClicked = onSeriesClicked,
        onStoryArcsClicked = onStoryArcsClicked,
        onTeamsClicked = onTeamsClicked,
        onVolumesClicked = onVolumesClicked,
        onBackPressed = onBackPressed
    )
}


fun NavGraphBuilder.categoriesComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            spring(stiffness = Spring.StiffnessMediumLow)
        )
    },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        { fadeOut() + scaleOut(targetScale = 0.9f) },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        { fadeIn() + scaleIn(initialScale = 0.9f) },
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            spring(stiffness = Spring.StiffnessMediumLow)
        )
    },
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable(
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
    enterTransition = enterTransition,
    exitTransition = exitTransition,
    popEnterTransition = popEnterTransition,
    popExitTransition = popExitTransition,
    content = content
)