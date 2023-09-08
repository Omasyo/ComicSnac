package com.keetr.comicsnac

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

        installSplashScreen()
//            .setKeepOnScreenCondition {
//            viewModel.apiKeyPresent
//        }
        val colorScheme = viewModel.selectedSchemeId.map {
            ColorSchemes[it]!!
        }
        val isPresent = viewModel.apiKeyPresent

        setContent {
            ComicSnacTheme(
                colorScheme.collectAsState(ColorSchemes[viewModel.initialSchemeId]!!, Dispatchers.Default).value
            ) {
                AppNavHost(apiKeyPresent = isPresent)
            }
        }

//        val content: View = findViewById(android.R.id.content)
//        content.viewTreeObserver.addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    // Check whether the initial data is ready.
//                    return if (!viewModel.apiKeyPresent) {
//                        // The content is ready. Start drawing.
//                        content.viewTreeObserver.removeOnPreDrawListener(this)
//                        true
//                    } else {
//                        // The content isn't ready. Suspend.
//                        false
//                    }
//                }
//            }
//        )

    }
}