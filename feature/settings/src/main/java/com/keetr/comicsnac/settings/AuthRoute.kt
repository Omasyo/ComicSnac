package com.keetr.comicsnac.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.ui.components.TextField
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

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