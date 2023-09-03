package com.keetr.comicsnac.settings

import androidx.compose.animation.AnimatedContent
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
import com.keetr.comicsnac.ui.components.TextField
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

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

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        AuthScreen(
            authUiState = AuthUiState.Initial,
            key = "",
            onVerifyClick = {},
            onVerificationComplete = {},
            onKeyChange = {}
        )
    }
}