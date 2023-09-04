package com.keetr.comicsnac.categories

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.categoriesNavigation(
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    onCharactersClicked: () -> Unit,
    onConceptsClicked: () -> Unit,
    onEpisodesClicked: () -> Unit,
    onIssuesClicked: () -> Unit,
    onLocationsClicked: () -> Unit,
    onMoviesClicked: () -> Unit,
    onObjectsClicked: () -> Unit,
    onOriginsClicked: () -> Unit,
    onPeopleClicked: () -> Unit,
    onPowersClicked: () -> Unit,
    onPublishersClicked: () -> Unit,
    onSeriesClicked: () -> Unit,
    onStoryArcsClicked: () -> Unit,
    onTeamsClicked: () -> Unit,
    onVolumesClicked: () -> Unit
) {
    navigation("/", "categories") {
        categoriesRoute(
            onCharactersClicked = onCharactersClicked,
            onConceptsClicked = onConceptsClicked,
            onEpisodesClicked = onEpisodesClicked,
            onIssuesClicked = onIssuesClicked,
            onLocationsClicked = onLocationsClicked,
            onMoviesClicked = onMoviesClicked,
            onObjectsClicked = onObjectsClicked,
            onOriginsClicked = onOriginsClicked,
            onPeopleClicked = onPeopleClicked,
            onPowersClicked = onPowersClicked,
            onPublishersClicked = onPublishersClicked,
            onSeriesClicked = onSeriesClicked,
            onStoryArcsClicked = onStoryArcsClicked,
            onTeamsClicked = onTeamsClicked,
            onVolumesClicked = onVolumesClicked,
            onBackPressed = onBackPressed
        )
    }
}