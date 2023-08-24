package com.keetr.comicsnac.details

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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.keetr.comicsnac.model.NavigationRoute


const val Domain = "https://comicvine.gamespot.com"
const val ApiBaseUrl = "$Domain/api/"

const val Arg = "id"

abstract class DetailsNavigationRoute(path: String, private val categoryId: String = "") :
    NavigationRoute("$path/$categoryId-%s/") {
    protected open val format = "$path/$categoryId-%s/"

    private val apiDeepLinkPattern get() = "$ApiBaseUrl$route"

    private val webDeepLinkPattern get() = "$Domain/{_}/$categoryId-{${requiredArguments.first()}}/"

    open val deepLinks
        get() = listOf(
            navDeepLink {
                uriPattern = apiDeepLinkPattern
            },
            navDeepLink {
                uriPattern = webDeepLinkPattern
            }
        )
}

fun NavGraphBuilder.detailsComposable(
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