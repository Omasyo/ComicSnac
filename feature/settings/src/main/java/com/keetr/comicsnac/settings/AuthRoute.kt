package com.keetr.comicsnac.settings

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keetr.comicsnac.model.NavigationRoute

object AuthRoute : NavigationRoute("/auth")

fun NavGraphBuilder.authRoute(
    modifier: Modifier = Modifier,
    onVerificationComplete: () -> Unit,
) = composable(
    route = AuthRoute.route,
    enterTransition = { fadeIn() + scaleIn(initialScale = 0.9f) },
    exitTransition = { fadeOut() + scaleOut(targetScale = 1.2f) },
) {
    AuthRoute(
        modifier = modifier,
        onVerificationComplete = onVerificationComplete,
    )
}

@Composable
fun AuthRoute(
    modifier: Modifier = Modifier,
    onVerificationComplete: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    AuthScreen(
        modifier = modifier,
        key = viewModel.key,
        onKeyChange = viewModel::onKeyChange,
        onVerifyClick = viewModel::verify,
        onVerificationComplete = onVerificationComplete,
        authUiState = viewModel.state
    )
}