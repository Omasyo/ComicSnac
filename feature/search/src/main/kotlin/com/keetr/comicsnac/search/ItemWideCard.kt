package com.keetr.comicsnac.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.search.SearchModel
import com.keetr.comicsnac.model.storyarc.StoryArc
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.ui.components.cards.CharacterWideCard
import com.keetr.comicsnac.ui.components.cards.ConceptWideCard
import com.keetr.comicsnac.ui.components.cards.IssueWideCard
import com.keetr.comicsnac.ui.components.cards.LocationWideCard
import com.keetr.comicsnac.ui.components.cards.ObjectWideCard
import com.keetr.comicsnac.ui.components.cards.StoryArcWideCard
import com.keetr.comicsnac.ui.components.cards.VolumeWideCard

@Composable
fun ItemWideCard(
    modifier: Modifier = Modifier,
    item: SearchModel,
    onItemClicked: (String) -> Unit
) {
    when (item) {
        is Character -> CharacterWideCard(
            modifier = modifier,
            character = item,
            onClick = onItemClicked
        )

        is Concept -> ConceptWideCard(
            modifier = modifier,
            concept = item,
            onClick = onItemClicked
        )

        is ObjectItem -> ObjectWideCard(
            modifier = modifier,
            objectItem = item,
            onClick = onItemClicked
        )

        is Location -> LocationWideCard(
            modifier = modifier,
            location = item,
            onClick = onItemClicked
        )

        is Issue -> IssueWideCard(
            modifier = modifier,
            issue = item,
            onClick = onItemClicked
        )

        is StoryArc -> StoryArcWideCard(
            modifier = modifier,
            storyArc = item,
            onClick = onItemClicked
        )

        is Volume -> VolumeWideCard(
            modifier = modifier,
            volume = item,
            onClick = onItemClicked
        )

        else -> throw NotImplementedError("Unknown type $item")
    }
}