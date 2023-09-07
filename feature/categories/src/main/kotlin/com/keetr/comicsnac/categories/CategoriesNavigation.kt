package com.keetr.comicsnac.categories

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.keetr.comicsnac.categories.character.CharacterRoute
import com.keetr.comicsnac.categories.character.characterRoute
import com.keetr.comicsnac.categories.character.navigateToCharacters
import com.keetr.comicsnac.categories.issue.issueRoute
import com.keetr.comicsnac.categories.issue.navigateToIssue
import com.keetr.comicsnac.categories.publisher.publisherRoute
import com.keetr.comicsnac.categories.team.teamRoute
import com.keetr.comicsnac.categories.volume.navigateToVolumes
import com.keetr.comicsnac.categories.volume.volumeRoute

fun NavGraphBuilder.categoriesNavigation(
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    navController: NavController
) {
    navigation("/", CategoriesRoute.route) {
        characterRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        issueRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        publisherRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        teamRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        volumeRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        categoriesRoute(
            onCharactersClicked = { navController.navigateToCharacters() },
            onConceptsClicked = { navController.navigateToVolumes() },
            onEpisodesClicked = { },
            onIssuesClicked = { navController.navigateToIssue() },
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
    }
}