package com.keetr.comicsnac.settings

import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keetr.comicsnac.model.NavigationRoute

private object ThemeRoute : NavigationRoute("/theme")

fun NavController.navigateToTheme(navOptions: NavOptions? = null) =
    navigate(ThemeRoute.route, navOptions)

fun NavGraphBuilder.themeRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) = composable(
    route = ThemeRoute.route,
    enterTransition = { fadeIn(spring()) + scaleIn(initialScale = 1.2f) },
    exitTransition = { fadeOut() + scaleOut(targetScale = 1.2f) },
) {
    ThemeRoute(
        modifier = modifier,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun ThemeRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    ThemeScreen(
        modifier = modifier,
        selectedSchemeId = viewModel.selectedSchemeId.collectAsState().value,
        onBackPressed = onBackPressed,
        onClickScheme = viewModel::onClickScheme
    )
}