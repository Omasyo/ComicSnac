package com.keetr.comicsnac.ui.components.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.model.search.SearchType
import com.keetr.comicsnac.ui.R


@Composable
fun IssueWideCard(
    modifier: Modifier = Modifier,
    issue: Issue,
    onClick: (String) -> Unit
) = with(issue) {
    WideCard(
        modifier = modifier,
        name = name,
        description = deck,
        onClick = { onClick(apiDetailUrl) },
        imageUrl = imageUrl,
        imageDescription = stringResource(
            R.string.issue_image_desc,
            issueNumber,
            volumeName,
            name
        ),
        type = SearchType.Issue.name,
    )
}