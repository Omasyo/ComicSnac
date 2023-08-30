package com.keetr.comicsnac.ui.components.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.ui.R


@Composable
fun VolumeWideCard(
    modifier: Modifier = Modifier,
    volume: Volume,
    onClick: (String) -> Unit
) = with(volume) {
    WideCard(
        modifier = modifier,
        name = name,
        description = deck,
        onClick = { onClick(apiDetailUrl) },
        imageUrl = imageUrl,
        imageDescription = stringResource(R.string.volume_image_desc, name),
        type = SearchType.Volume.name,
    )
}