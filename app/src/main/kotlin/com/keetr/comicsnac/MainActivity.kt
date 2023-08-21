package com.keetr.comicsnac

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.keetr.comicsnac.details.character.characterRoute
import com.keetr.comicsnac.home.HomeRoute
import com.keetr.comicsnac.home.homeRoute
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            ComicSnacTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HomeRoute.route
                ) {
                    val onItemClicked = { guid: String ->
                        navController.navigate(
                            Uri.parse(guid)
                        )
                    }

                    homeRoute(
                        onItemClicked = onItemClicked,
                        onMoreCategoriesClicked = {},
                        onCharacterCategoryClicked = {},
                        onVolumeCategoryClicked = {},
                        onMovieCategoryClicked = {},
                        onSeriesCategoryClicked = {}
                    )

                    characterRoute(
                        onItemClicked = onItemClicked,
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}