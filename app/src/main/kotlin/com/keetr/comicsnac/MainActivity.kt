package com.keetr.comicsnac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.keetr.comicsnac.ui.theme.ColorSchemes
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        val colorScheme = viewModel.selectedSchemeId.map {
            ColorSchemes[it]!!
        }
        val isPresent = viewModel.apiKeyPresent

        setContent {
            ComicSnacTheme(
                colorScheme.collectAsState(
                    ColorSchemes[viewModel.initialSchemeId]!!,
                    Dispatchers.Default
                ).value
            ) {
                AppNavHost(apiKeyPresent = isPresent)
            }
        }
    }
}