package com.keetr.comicsnac.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (apiDetailUrl: String) -> Unit,
    onMoreCategoriesClicked: () -> Unit,
    onCharacterCategoryClicked: () -> Unit,
    onVolumeCategoryClicked: () -> Unit,
    onMovieCategoryClicked: () -> Unit,
    onSeriesCategoryClicked: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val charactersUiState by viewModel.charactersUiState.collectAsState()
    val issueUiState by viewModel.issueUiState.collectAsState()
    HomeScreen(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onMoreCategoriesClicked = onMoreCategoriesClicked,
        onCharacterCategoryClicked = onCharacterCategoryClicked,
        onVolumeCategoryClicked = onVolumeCategoryClicked,
        onMovieCategoryClicked = onMovieCategoryClicked,
        onSeriesCategoryClicked = onSeriesCategoryClicked,
        homeUiState = HomeUiState(
            issuesUiState = issueUiState,
            charactersUiState = charactersUiState,
            volumesUiState = InDevelopment,
            moviesUiState = InDevelopment,
            seriesUiState = InDevelopment,
            publishersUiState = InDevelopment

        )
    )
}