package com.keetr.comicsnac.categories.team

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
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.cards.WideCard

private object TeamRoute : NavigationRoute("teams")

internal fun NavGraphBuilder.teamRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(TeamRoute.route) {
    TeamRoute(onItemClicked = onItemClicked, onBackPressed = onBackPressed)
}

fun NavController.navigateToTeams(navOptions: NavOptions? = null) =
    navigate(TeamRoute.route, navOptions)

@Composable
internal fun TeamRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: TeamViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.teams),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { team ->
            WideCard(
                name = team.name,
                description = team.deck,
                onClick = { onItemClicked(team.apiDetailUrl) },
                imageUrl = team.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.team_image_desc, team.name
                )
            )
        }
    ) { team ->
        ComicCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = team.name,
            imageUrl = team.imageUrl,
            contentDescription = stringResource(
                R.string.team_image_desc, team.name
            ),
            onClick = { onItemClicked(team.apiDetailUrl) }
        )
    }
}