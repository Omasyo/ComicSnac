package com.keetr.comicsnac.categories.person

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

private object PersonRoute : NavigationRoute("persons")

internal fun NavGraphBuilder.personRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) = composable(PersonRoute.route) {
    PersonRoute(
        modifier = modifier,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

fun NavController.navigateToPeople(navOptions: NavOptions? = null) =
    navigate(PersonRoute.route, navOptions)

@Composable
internal fun PersonRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: PersonViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.people),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.items.collectAsLazyPagingItems(),
        listContentBuilder = { person ->
            WideCard(
                name = person.name,
                description = person.deck,
                onClick = { onItemClicked(person.apiDetailUrl) },
                imageUrl = person.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.person_image_desc, person.name
                )
            )
        }
    ) { person ->
        PlainCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = person.name,
            imageUrl = person.imageUrl,
            contentDescription = stringResource(
                R.string.person_image_desc, person.name
            ),
            onClick = { onItemClicked(person.apiDetailUrl) }
        )
    }
}