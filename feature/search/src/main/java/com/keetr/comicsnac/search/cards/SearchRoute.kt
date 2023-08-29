package com.keetr.comicsnac.search.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.search.SearchScreen
import com.keetr.comicsnac.search.SearchViewModel

private object SearchRoute : NavigationRoute("search")

fun NavController.navigateToSearch() = navigate(SearchRoute.route)

fun NavGraphBuilder.searchRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(SearchRoute.route) {
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
        query = viewModel.query,
        onQueryChanged = viewModel::onQueryChanged,
        filter = viewModel.filters,
        onFilterChange = viewModel::onFilterChange,
        onItemClicked = onItemClicked,
        onSearch = viewModel::onSearch,
        onBackPressed = onBackPressed,
        searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    )
}