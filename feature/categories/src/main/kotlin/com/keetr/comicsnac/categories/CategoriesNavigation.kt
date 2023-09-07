package com.keetr.comicsnac.categories

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.keetr.comicsnac.categories.character.characterRoute
import com.keetr.comicsnac.categories.character.navigateToCharacters
import com.keetr.comicsnac.categories.issue.issueRoute
import com.keetr.comicsnac.categories.issue.navigateToIssue
import com.keetr.comicsnac.categories.`object`.navigateToObjects
import com.keetr.comicsnac.categories.`object`.objectRoute
import com.keetr.comicsnac.categories.person.navigateToPeople
import com.keetr.comicsnac.categories.person.personRoute
import com.keetr.comicsnac.categories.power.navigateToPowers
import com.keetr.comicsnac.categories.power.powerRoute
import com.keetr.comicsnac.categories.publisher.navigateToPublishers
import com.keetr.comicsnac.categories.publisher.publisherRoute
import com.keetr.comicsnac.categories.team.navigateToTeams
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

        objectRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        personRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        powerRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        publisherRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        teamRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        volumeRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)

        categoriesRoute(
            onCharactersClicked = { navController.navigateToCharacters() },
            onConceptsClicked = { },
            onEpisodesClicked = { },
            onIssuesClicked = { navController.navigateToIssue() },
            onLocationsClicked = { },
            onMoviesClicked = { },
            onObjectsClicked = { navController.navigateToObjects() },
            onOriginsClicked = { },
            onPeopleClicked = { navController.navigateToPeople() },
            onPowersClicked = { navController.navigateToPowers() },
            onPublishersClicked = { navController.navigateToPublishers() },
            onSeriesClicked = { },
            onStoryArcsClicked = { },
            onTeamsClicked = { navController.navigateToTeams() },
            onVolumesClicked = { navController.navigateToVolumes() },
            onBackPressed = onBackPressed
        )
    }
}