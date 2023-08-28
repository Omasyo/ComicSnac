package com.keetr.comicsnac.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.ui.R


@Composable
fun ObjectWideCard(
    modifier: Modifier = Modifier,
    objectItem: ObjectItem,
    onClick: (String) -> Unit
) = with(objectItem) {
    WideCard(
        modifier = modifier,
        name = name,
        description = deck,
        onClick = { onClick(apiDetailUrl) },
        imageUrl = imageUrl,
        imageDescription = stringResource(R.string.object_image_desc, name),
        type = SearchType.Object.name,
    )
}