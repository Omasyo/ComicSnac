package com.keetr.comicsnac

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keetr.comicsnac.details.character.characterRoute
import com.keetr.comicsnac.details.issue.issueRoute
import com.keetr.comicsnac.details.team.teamRoute
import com.keetr.comicsnac.home.HomeRoute
import com.keetr.comicsnac.home.homeRoute
import com.keetr.comicsnac.search.cards.searchRoute
import com.keetr.comicsnac.ui.components.placeholders.InDevelopmentPlaceholder

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "search"
    ) {
        val onBackPressed: () -> Unit = { navController.popBackStack() }
        val onItemClicked = { guid: String ->
            try {
                navController.navigate(
                    Uri.parse(guid)
                )
            } catch (e: IllegalArgumentException) {
                Log.e("AppNavHost", "AppNavHost: ${e.message}")
                navController.navigate("error")
            }
        }

        homeRoute(
            onItemClicked = onItemClicked,
            onMoreCategoriesClicked = {},
            onCharacterCategoryClicked = {},
            onVolumeCategoryClicked = {},
            onMovieCategoryClicked = {},
            onSeriesCategoryClicked = {}
        )

        searchRoute(
            onItemClicked = onItemClicked,
            onBackPressed = onBackPressed
        )

        characterRoute(
            onItemClicked = onItemClicked,
            onBackPressed = onBackPressed
        )

        issueRoute(
            onItemClicked = onItemClicked,
            onBackPressed = onBackPressed
        )

        teamRoute(
            onItemClicked = onItemClicked,
            onBackPressed = onBackPressed
        )

        composable(
            "error",
            enterTransition = { fadeIn(spring()) + scaleIn(initialScale = 1.2f) },
            popExitTransition = {
                fadeOut(spring(stiffness = Spring.StiffnessMedium)) + scaleOut(
                    spring(stiffness = Spring.StiffnessMedium),
                    targetScale = 1.2f
                )
            }
        ) {
            Surface {
                Box {
                    InDevelopmentPlaceholder(Modifier.fillMaxSize())

                    Text(
                        "Go Back",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .padding(bottom = 64f.dp)
                            .clickable { navController.popBackStack() }
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(16f.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}