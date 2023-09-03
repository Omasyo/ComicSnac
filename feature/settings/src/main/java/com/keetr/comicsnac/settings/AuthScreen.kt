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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.components.TextField
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    key: String,
    onKeyChange: (String) -> Unit,
    onVerifyClick: () -> Unit,
    onVerificationComplete: () -> Unit,
    authUiState: AuthUiState,
) {
    LaunchedEffect(key1 = authUiState) {
        if(authUiState == AuthUiState.Verified) {
            onVerificationComplete()
        }
    }

    Scaffold(modifier) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32f.dp, Alignment.CenterVertically)
        ) {
            Text(
                "ComicSnac works with the ComicVine API.\nTap the button below to get an API key",
                textAlign = TextAlign.Center
            )
            ComicCard(
                backgroundColor = MaterialTheme.colorScheme.onError,
                onClick = {}
            ) {
                Text(
                    "Get API Key",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(12f.dp, 8f.dp)
                )
            }
            Text("Please enter your API key to continue")
            TextField(
                value = key,
                onValueChange = onKeyChange,
                placeholder = "Enter API Key",
                textStyle = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
            )
            AnimatedContent(targetState = authUiState, label = "verify_button") { state ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16f.dp)
                ) {
                    if (state == AuthUiState.InvalidKey) {
                        Text(
                            "The API Key entered is not valid",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if (state == AuthUiState.UnknownError) {
                        Text(
                            stringResource(com.keetr.comicsnac.ui.R.string.error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    when (state) {
                        AuthUiState.Initial, AuthUiState.InvalidKey, AuthUiState.UnknownError -> {
                            ComicCard(
                                backgroundColor = MaterialTheme.colorScheme.onError,
                                onClick = onVerifyClick
                            ) {
                                Text(
                                    "Verify",
                                    maxLines = 1,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(12f.dp, 8f.dp)
                                )
                            }
                        }
                        AuthUiState.Verifying -> {
                            LoadingPlaceholder(color = MaterialTheme.colorScheme.error)
                        }
                        AuthUiState.Verified -> { }
                    }
                }
            }
        }
    }
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