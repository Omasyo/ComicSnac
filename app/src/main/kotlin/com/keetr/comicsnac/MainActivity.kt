package com.keetr.comicsnac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.keetr.comicsnac.ui.theme.ColorSchemes
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.DefaultScheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        val colorScheme = viewModel.selectedSchemeId.map {
            ColorSchemes[it] ?: DefaultScheme
        }

        setContent {
            ComicSnacTheme(
                colorScheme.collectAsState(DefaultScheme).value
            ) {
                AppNavHost()
            }
        }
    }
}