package com.keetr.comicsnac.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.ui.R


@Composable
fun ConceptWideCard(
    modifier: Modifier = Modifier,
    concept: Concept,
    onClick: (String) -> Unit
) = with(concept) {
    WideCard(
        modifier = modifier,
        name = name,
        description = deck,
        onClick = { onClick(apiDetailUrl) },
        imageUrl = imageUrl,
        imageDescription = stringResource(R.string.concept_image_desc, name),
        type = SearchType.Concept.name,
    )
}