package com.keetr.comicsnac.categories

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keetr.comicsnac.model.NavigationRoute


internal object CategoriesRoute : NavigationRoute("categories")

fun NavController.navigateToCategories(navOptions: NavOptions? = null) =
    navigate(CategoriesRoute.route, navOptions)

fun NavGraphBuilder.categoriesRoute(
    modifier: Modifier = Modifier,
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
    onVolumesClicked: () -> Unit,
    onBackPressed: () -> Unit
) = composable(
    "/"
) {
    CategoriesScreen(
        modifier = modifier,
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