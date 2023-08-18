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
                    homeRoute(
                        onItemClicked = {
//                            navController.navigate("/characters/TheId")
                            navController.navigate(
                                Uri.parse(it)
                            )
                        },
                        onMoreCategoriesClicked = {},
                        onCharacterCategoryClicked = {},
                        onVolumeCategoryClicked = {},
                        onMovieCategoryClicked = {},
                        onSeriesCategoryClicked = {
                        }
                    )

                    composable(
                        route = "/characters/{guid}",
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "https://comicvine.gamespot.com/api/character/{guid}/"
                            },
                            navDeepLink {
                                uriPattern = "https://comicvine.gamespot.com/{temp}/{guid}/"
                            }
                        )
                    ) { backStackEntry ->

                        val guid = checkNotNull(backStackEntry.arguments?.getString("guid"))
                        val route = backStackEntry

                        Surface(Modifier.fillMaxSize()) {
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    guid,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                Text(
                                    guid,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}