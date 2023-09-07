package com.keetr.comicsnac.categories.issue

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.categories.CategoryScreen
import com.keetr.comicsnac.model.NavigationRoute
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.cards.WideCard

private object IssuesRoute : NavigationRoute("issues_list")

internal fun NavGraphBuilder.issueRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(IssuesRoute.route) {
    IssueRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)
}

fun NavController.navigateToIssue(navOptions: NavOptions? = null) =
    navigate(IssuesRoute.route, navOptions)

@Composable
internal fun IssueRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: IssueViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.issues),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { issue ->
            WideCard(
                name = issue.name,
                description = issue.deck,
                onClick = { onItemClicked(issue.apiDetailUrl) },
                imageUrl = issue.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.issue_image_desc, issue
                )
            )
        }
    ) { issue ->
        PlainCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = issue.name,
            imageUrl = issue.imageUrl,
            contentDescription = stringResource(
                R.string.issue_image_desc, issue.volumeName, issue.issueNumber, issue.name
            ),
            onClick = { onItemClicked(issue.apiDetailUrl) }
        )
    }
}