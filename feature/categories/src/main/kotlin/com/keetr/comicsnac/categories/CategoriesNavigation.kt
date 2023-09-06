package com.keetr.comicsnac.categories

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.keetr.comicsnac.categories.character.CharacterRoute

fun NavGraphBuilder.categoriesNavigation(
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    navController: NavController
) {
    navigation("/", "categories") {
        categoriesRoute(
            onCharactersClicked = { navController.navigate("characters") },
            onConceptsClicked = { },
            onEpisodesClicked = { },
            onIssuesClicked = { },
            onLocationsClicked = { },
            onMoviesClicked = { },
            onObjectsClicked = { },
            onOriginsClicked = { },
            onPeopleClicked = { },
            onPowersClicked = { },
            onPublishersClicked = { },
            onSeriesClicked = { },
            onStoryArcsClicked = { },
            onTeamsClicked = { },
            onVolumesClicked = { },
            onBackPressed = onBackPressed
        )

        composable("characters") {
            CharacterRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)
        }
    }
}