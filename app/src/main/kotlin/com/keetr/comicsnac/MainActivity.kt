package com.keetr.comicsnac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.keetr.comicsnac.ui.theme.ColorSchemes
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.DefaultScheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        val initial = runBlocking {
//            viewModel.selectedSchemeId.first()
//        }

        val colorScheme = viewModel.selectedSchemeId.map {
            ColorSchemes[it] ?: DefaultScheme
        }
        val isPresent = viewModel.apiKeyPresent

        setContent {
            ComicSnacTheme(
                colorScheme.collectAsState(DefaultScheme, Dispatchers.Default).value
            ) {
                AppNavHost(apiKeyPresent = isPresent)
            }
        }
    }
}