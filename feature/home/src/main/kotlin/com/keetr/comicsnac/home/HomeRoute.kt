package com.keetr.comicsnac.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keetr.comicsnac.model.NavigationRoute


object HomeRoute : NavigationRoute("/")

fun NavGraphBuilder.homeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onSearchClicked: () -> Unit,
    onMoreCategoriesClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onCharacterCategoryClicked: () -> Unit,
    onVolumeCategoryClicked: () -> Unit,
    onMovieCategoryClicked: () -> Unit,
    onSeriesCategoryClicked: () -> Unit,
) = composable(
    route = HomeRoute.route,
    enterTransition = { fadeIn() + scaleIn(initialScale = 0.9f) },
    exitTransition = { fadeOut() + scaleOut(targetScale = 0.9f) },
) {
    HomeRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onSearchClicked = onSearchClicked,
        onMoreCategoriesClicked = onMoreCategoriesClicked,
        onSettingsClicked = onSettingsClicked,
        onCharacterCategoryClicked = onCharacterCategoryClicked,
        onVolumeCategoryClicked = onVolumeCategoryClicked,
        onMovieCategoryClicked = onMovieCategoryClicked,
        onSeriesCategoryClicked = onSeriesCategoryClicked
    )
}


@Composable
private fun HomeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onSearchClicked: () -> Unit,
    onMoreCategoriesClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onCharacterCategoryClicked: () -> Unit,
    onVolumeCategoryClicked: () -> Unit,
    onMovieCategoryClicked: () -> Unit,
    onSeriesCategoryClicked: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onSearchClicked = onSearchClicked,
        onMoreCategoriesClicked = onMoreCategoriesClicked,
        onSettingsClicked = onSettingsClicked,
        onCharacterCategoryClicked = onCharacterCategoryClicked,
        onVolumeCategoryClicked = onVolumeCategoryClicked,
        onMovieCategoryClicked = onMovieCategoryClicked,
        onSeriesCategoryClicked = onSeriesCategoryClicked,
        issuesUiState = viewModel.issueUiState.collectAsState().value,
        charactersUiState = viewModel.charactersUiState.collectAsState().value,
        volumesUiState = viewModel.volumeUiState.collectAsState().value,
        moviesUiState = InDevelopment,
        seriesUiState = InDevelopment,
        publishersUiState = viewModel.publishersUiState.collectAsState().value
    )
}