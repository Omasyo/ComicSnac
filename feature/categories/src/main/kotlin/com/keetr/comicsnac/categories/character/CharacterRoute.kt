package com.keetr.comicsnac.categories.character

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.keetr.comicsnac.categories.CategoryScreen
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.cards.WideCard

@Composable
internal fun CharacterRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    CategoryScreen(
        modifier = modifier,
        title = stringResource(R.string.characters),
        onBackPressed = onBackPressed,
        layoutType = viewModel.layoutType.collectAsState().value,
        onToggleLayoutType = viewModel::onToggleLayout,
        items = viewModel.characters.collectAsLazyPagingItems(),
        listContentBuilder = { character ->
            WideCard(
                name = character.name,
                description = character.deck,
                onClick = { onItemClicked(character.apiDetailUrl) },
                imageUrl = character.imageUrl,
                type = "",
                imageDescription = stringResource(
                    R.string.character_image_desc, character.name
                )
            )
        }
    ) { character ->
        ComicCard(
            modifier = Modifier.aspectRatio(6f / 11f),
            name = character.name,
            imageUrl = character.imageUrl,
            contentDescription = stringResource(
                R.string.character_image_desc, character.name
            ),
            onClick = { onItemClicked(character.apiDetailUrl) }
        )
    }
}