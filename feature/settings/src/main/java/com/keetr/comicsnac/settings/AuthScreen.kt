package com.keetr.comicsnac.settings

import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.components.TextField
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import okhttp3.internal.toHexString

const val ApiKeyUrl = "https://comicvine.gamespot.com/api/"

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
        if (authUiState == AuthUiState.Verified) {
            onVerificationComplete()
        }
    }

    Scaffold(modifier) { innerPadding ->
        Column(
            Modifier
                .testTag("auth_screen")
                .padding(innerPadding)
                .padding(16f.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32f.dp, Alignment.CenterVertically)
        ) {
            Text(
                stringResource(R.string.api_key_reason),
                textAlign = TextAlign.Center
            )
            val context = LocalContext.current
            val colorScheme = MaterialTheme.colorScheme
            ComicCard(
                backgroundColor = MaterialTheme.colorScheme.onError,
                onClick = {
                    val intent = CustomTabsIntent.Builder().run {
                        val params = CustomTabColorSchemeParams.Builder().run {
                            setNavigationBarColor(colorScheme.primary.toArgb())
                            setToolbarColor(colorScheme.tertiary.toArgb())
                            build()
                        }
                        setDefaultColorSchemeParams(params)
                        build()
                    }

                    intent.launchUrl(context, Uri.parse(ApiKeyUrl))
                }
            ) {
                Text(
                    stringResource(R.string.get_api_key),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(12f.dp, 8f.dp)
                )
            }
            Text(stringResource(R.string.request_enter_api_key))
            TextField(
                modifier = Modifier.testTag("text_field").padding(12f.dp, 8f.dp),
                value = key,
                onValueChange = onKeyChange,
                placeholder = stringResource(R.string.enter_api_key),
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
            AnimatedContent(targetState = authUiState, label = "verify_button") { state ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16f.dp)
                ) {
                    if (state == AuthUiState.InvalidKey) {
                        Text(
                            stringResource(R.string.invalid_api_key),
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
                                modifier = Modifier.testTag("verify_button"),
                                backgroundColor = MaterialTheme.colorScheme.onError,
                                onClick = onVerifyClick
                            ) {
                                Text(
                                    stringResource(R.string.verify),
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

                        AuthUiState.Verified -> {}
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