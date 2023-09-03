package com.keetr.comicsnac.search

import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.model.NavigationRoute

private object SearchRoute : NavigationRoute("search")

fun NavController.navigateToSearch() = navigate(SearchRoute.route)

fun NavGraphBuilder.searchRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(
    SearchRoute.route,
    enterTransition = { fadeIn(spring()) + scaleIn(initialScale = 1.2f) },
    exitTransition = { fadeOut() + scaleOut(targetScale = 0.9f) },
    popEnterTransition = { fadeIn() + scaleIn(initialScale = 0.9f) },
    popExitTransition = { fadeOut() + scaleOut(targetScale = 1.2f) }
) {
    SearchRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreen(
        modifier = modifier,
        queryProvider = { viewModel.query },
        onQueryChanged = viewModel::onQueryChanged,
        searchEmpty = viewModel.submittedQuery.collectAsState().value.isBlank(),
        filter = viewModel.filters,
        onClickFilter = viewModel::toggleFilter,
        onLongClickFilter = viewModel::selectFilter,
        onItemClicked = onItemClicked,
        onSearch = viewModel::onSearch,
        onClear = viewModel::clearQuery,
        onBackPressed = onBackPressed,
        searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    )
}