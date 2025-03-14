package com.keetr.comicsnac.categories

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCharactersClicked: () -> Unit,
    onConceptsClicked: () -> Unit,
    onEpisodesClicked: () -> Unit,
    onIssuesClicked: () -> Unit,
    onLocationsClicked: () -> Unit,
    onMoviesClicked: () -> Unit,
    onObjectsClicked: () -> Unit,
    onOriginsClicked: () -> Unit,
    onPeopleClicked: () -> Unit,
    onPowersClicked: () -> Unit,
    onPublishersClicked: () -> Unit,
    onSeriesClicked: () -> Unit,
    onStoryArcsClicked: () -> Unit,
    onTeamsClicked: () -> Unit,
    onVolumesClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    with(MaterialTheme.colorScheme) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(title = { Text("Categories") }, navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(AppIcons.ArrowBack, stringResource(R.string.back_button_desc))
                    }
                })
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
            ) {
                Separator()
                Category(R.string.characters, onCharactersClicked, tertiary)
                Separator()
                Category(R.string.concepts, onConceptsClicked, secondary)
                Separator()
                Category(R.string.episodes, onEpisodesClicked, tertiary)
                Separator()
                Category(R.string.issues, onIssuesClicked, secondary)
                Separator()
                Category(R.string.locations, onLocationsClicked, tertiary)
                Separator()
                Category(R.string.movies, onMoviesClicked, secondary)
                Separator()
                Category(R.string.objects, onObjectsClicked, tertiary)
                Separator()
                Category(R.string.origins, onOriginsClicked, secondary)
                Separator()
                Category(R.string.people, onPeopleClicked, tertiary)
                Separator()
                Category(R.string.powers, onPowersClicked, secondary)
                Separator()
                Category(R.string.publishers, onPublishersClicked, tertiary)
                Separator()
                Category(R.string.series, onSeriesClicked, secondary)
                Separator()
                Category(R.string.story_arcs, onStoryArcsClicked, tertiary)
                Separator()
                Category(R.string.teams, onTeamsClicked, secondary)
                Separator()
                Category(R.string.volumes, onVolumesClicked, tertiary)
                Separator()
            }
        }
    }
}

@Composable
private fun Category(
    @StringRes nameId: Int,
    onClick: () -> Unit,
    backgroundColor: Color
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(horizontal = 32f.dp, vertical = 20f.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(nameId), style = MaterialTheme.typography.titleMedium)
        Icon(
            imageVector = AppIcons.RightArrow,
            contentDescription = stringResource(
                com.keetr.comicsnac.categories.R.string.expand_category_desc,
                stringResource(nameId)
            )
        )
    }
}

@Composable
private fun Separator() {
    HorizontalDivider(thickness = 4f.dp)
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        CategoriesScreen(
            onCharactersClicked = { /*TODO*/ },
            onConceptsClicked = { /*TODO*/ },
            onEpisodesClicked = { /*TODO*/ },
            onIssuesClicked = { /*TODO*/ },
            onLocationsClicked = { /*TODO*/ },
            onMoviesClicked = { /*TODO*/ },
            onObjectsClicked = { /*TODO*/ },
            onOriginsClicked = { /*TODO*/ },
            onPeopleClicked = { /*TODO*/ },
            onPowersClicked = { /*TODO*/ },
            onPublishersClicked = { /*TODO*/ },
            onSeriesClicked = { /*TODO*/ },
            onStoryArcsClicked = { /*TODO*/ },
            onTeamsClicked = { /*TODO*/ },
            onVolumesClicked = { /*TODO*/ }) {

        }
    }
}