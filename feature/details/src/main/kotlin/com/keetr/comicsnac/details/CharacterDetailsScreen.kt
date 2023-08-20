package com.keetr.comicsnac.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.origin.OriginBasic
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.ui.R.string as CommonString
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlinx.coroutines.launch

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: CharacterDetailsUiState,
    enemiesUiState: CharactersUiState,
    friendsUiState: CharactersUiState,
    moviesUiState: MoviesUiState,
    teamsUiState: TeamsUiState,
    teamEnemiesUiState: TeamsUiState,
    teamFriendsUiState: TeamsUiState,
    volumeUiState: VolumeUiState
) {
    when (detailsUiState) {
        is Error -> TODO()
        InDevelopment -> TODO()
        Loading -> {
            TODO()
        }
        is Success -> {
            val scope = rememberCoroutineScope()

            var imageExpanded by remember {
                mutableStateOf(false)
            }


            val state = rememberLazyListState()
            var expandedIndex by remember {
                mutableIntStateOf(-1)
            }
            val canScroll = expandedIndex < 0 && !imageExpanded

            suspend fun onExpand(index: Int) {
                if (expandedIndex == index) {
                    expandedIndex = -1
                    state.animateScrollAndAlignItem(
                        index, 0.33f
                    )
                } else {
                    expandedIndex = index
                    state.animateScrollAndAlignItem(
                        index
                    )
                }
            }

            BackHandler(!canScroll) {
                imageExpanded = false
                expandedIndex = -1
            }

            with(detailsUiState.content) {
                DetailsScreen(
                    modifier = modifier,
                    images = listOf(
                        Image(
                            imageUrl,
                            stringResource(CommonString.character_image_desc)
                        )
                    ),
                    lazyListState = state,
                    userScrollEnabled = canScroll,
                    onBackPressed = onBackPressed,
                    onImageClose = { imageExpanded = false },
                    imageExpanded = imageExpanded,
                    onImageClicked = {
                        scope.launch {
                            imageExpanded = true
                            state.scrollToItem(0)
                        }
                    },

                    ) {
                    panel {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16f.dp, vertical = 8f.dp),
                            verticalArrangement = Arrangement.spacedBy(4f.dp),
                        ) {
                            Text(name, style = MaterialTheme.typography.headlineMedium)
                            Text(deck, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    panel {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16f.dp, vertical = 4f.dp),
                            verticalArrangement = Arrangement.spacedBy(4f.dp)
                        ) {
                            Info(name = stringResource(R.string.real_name), content = name)
                            origin?.let {
                                Info(name = stringResource(R.string.origin), content = it.name) {
                                    onItemClicked(it.apiDetailUrl)
                                }
                            }
                            Info(name = stringResource(CommonString.gender), content = gender.name)
                            Info(
                                name = stringResource(CommonString.aliases),
                                content = aliases.joinToString(", ")
                            )
                            Info(
                                name = stringResource(R.string.first_appeared_in_issue),
                                content = firstAppearance.name
                            ) {
                                onItemClicked(firstAppearance.apiDetailUrl)
                            }
                            Info(
                                name = stringResource(R.string.issue_appearances),
                                content = countOfIssueAppearances.toString()
                            )
                            publisher?.let {
                                Info(name = stringResource(R.string.publisher), content = it.name) {
                                    onItemClicked(it.apiDetailUrl)
                                }
                            }
                        }
                    }

                    panelSeparator()

                    panel {
                        DetailsFlow(
                            name = stringResource(CommonString.powers),
                            items = powers
                        ) { power ->
                            Text(
                                power.name,
                                Modifier
                                    .clickable { onItemClicked(apiDetailUrl) }
                                    .padding(horizontal = 8f.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(CommonString.friends),
                            uiState = friendsUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { character ->
                            ComicCard(
                                modifier = Modifier.width(136f.dp),
                                name = character.name,
                                imageUrl = character.imageUrl,
                                contentDescription = stringResource(
                                    CommonString.character_image_desc, character.name
                                ),
                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(CommonString.enemies),
                            uiState = enemiesUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { character ->
                            ComicCard(
                                modifier = Modifier.width(136f.dp),
                                name = character.name,
                                imageUrl = character.imageUrl,
                                contentDescription = stringResource(
                                    CommonString.character_image_desc, character.name
                                ),
                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(CommonString.teams),
                            uiState = teamsUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { team ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = team.name,
//                                imageUrl = team.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(R.string.team_friends),
                            uiState = teamFriendsUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { team ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = character.name,
//                                imageUrl = character.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(R.string.team_enemies),
                            uiState = teamEnemiesUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { team ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = character.name,
//                                imageUrl = character.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(CommonString.volumes),
                            uiState = volumeUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { volume ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = character.name,
//                                imageUrl = character.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel { index ->
                        DetailsGrid(
                            name = stringResource(CommonString.movies),
                            uiState = moviesUiState,
                            expanded = expandedIndex == index,
                            onToggleExpand = {
                                scope.launch { onExpand(index) }
                            },
                            key = { it.id }
                        ) { movie ->
//                            ComicCard(
//                                modifier = Modifier.width(136f.dp),
//                                name = character.name,
//                                imageUrl = character.imageUrl,
//                                contentDescription = stringResource(
//                                    R.string.character_image_desc, character.name
//                                ),
//                                onClick = { onItemClicked(character.apiDetailUrl) })
                        }
                    }

                    panelSeparator()

                    panel {
                        DetailsFlow(
                            name = stringResource(CommonString.creators),
                            items = creators
                        ) { person ->
                            Text(
                                person.name,
                                Modifier
                                    .clickable { onItemClicked(apiDetailUrl) }
                                    .padding(horizontal = 16f.dp),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    creators
                }

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T> LazyItemScope.DetailsFlow(
    modifier: Modifier = Modifier,
    name: String,
    items: List<T>,
    builder: @Composable (T) -> Unit
) {
    Column(modifier) {
        Text(
            name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16f.dp, top = 8f.dp)
        )
        FlowRow(
            Modifier
                .fillMaxWidth()
                .padding(16f.dp),
            verticalArrangement = Arrangement.spacedBy(32f.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (item in items) {
                builder(item)
            }
        }
    }
}

@Composable
private fun Info(
    name: String,
    content: String,
    onItemClicked: (() -> Unit)? = null
) {
    Row(Modifier.fillMaxWidth()) {
        Text("$name: ", style = MaterialTheme.typography.titleLarge)
        Text(
            content,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable(onItemClicked != null) { onItemClicked?.invoke() })
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {

        CharacterDetailsScreen(
            onItemClicked = {},
            onBackPressed = { /*TODO*/ },
            detailsUiState = Success(Character),
            enemiesUiState = InDevelopment,
            friendsUiState = Success(Friends),
            moviesUiState = InDevelopment,
            teamsUiState = InDevelopment,
            teamEnemiesUiState = InDevelopment,
            teamFriendsUiState = InDevelopment,
            volumeUiState = InDevelopment,
        )
    }
}

val Friends = List(40) {
    Character(
        apiDetailUrl = "https://duckduckgo.com/?q=explicari",
        deck = "menandri",
        id = it,
        imageUrl = "https://search.yahoo.com/search?p=aperiri",
        name = "Kenneth Pennington",
        siteDetailUrl = "https://www.google.com/#q=intellegebat"
    )
}

val Character = CharacterDetails(
    id = 4367,
    aliases = listOf(
        "sdfasdg",
        "sdfgsdgf",
        "fasasdf",
        "dfsdf",
        "sdfas",
        "sdfasdg",
        "sdfgsdgf",
        "fasasdf",
        "dfsdf",
        "sdfas"
    ),
    apiDetailUrl = "https://duckduckgo.com/?q=libris",
    countOfIssueAppearances = 9035,
    creators = listOf(),
    deck = "urbanitas foasdjf asdf asd fa sdf asdf as df as dfasdf a sdf as df asd fasdf asdfasdf asdfasdf asdfasdf asdfasdf asf",
    description = "eius",
    firstAppearance = IssueBasic(
        apiDetailUrl = "http://www.bing.com/search?q=mucius",
        id = 4695,
        name = "Cecilia Blevins"
    ),
    enemiesId = listOf(),
    friendsId = listOf(),
    gender = Gender.Female,
    imageUrl = "https://www.google.com/#q=vehicula",
    moviesId = listOf(),
    name = "Marina Castillo",
    origin = OriginBasic(
        apiDetailUrl = "https://search.yahoo.com/search?p=tristique",
        id = 3671,
        name = "August Winters"
    ),
    powers = List(20) {
        PowerBasic(
            apiDetailUrl = "https://duckduckgo.com/?q=euripidis",
            id = 5718,
            name = "Allyson Barnes"
        )
    },
    publisher = null,
    realName = "Lance Walsh",
    siteDetailUrl = "http://www.bing.com/search?q=elaboraret",
    teamEnemiesId = listOf(),
    teamFriendsId = listOf(),
    teamsId = listOf(),
    volumeCreditsId = listOf()

)