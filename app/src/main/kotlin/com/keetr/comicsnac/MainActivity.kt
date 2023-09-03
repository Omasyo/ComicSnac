package com.keetr.comicsnac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.intPreferencesKey
import com.keetr.comicsnac.data.settings.dataStore
import com.keetr.comicsnac.ui.theme.ColorSchemes
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.DefaultScheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        val ColorSchemeKey = intPreferencesKey("color_scheme")
        val colorScheme: Flow<ColorScheme> = baseContext.dataStore.data.map { preferences ->
            ColorSchemes[preferences[ColorSchemeKey]] ?: DefaultScheme
        }

        super.onCreate(savedInstanceState)
        setContent {
            ComicSnacTheme(
                colorScheme.collectAsState(DefaultScheme).value
            ) {
                AppNavHost()
            }
        }
    }
}