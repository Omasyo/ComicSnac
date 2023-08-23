package com.keetr.comicsnac.details.character

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.details.CharacterDetailsUiState
import com.keetr.comicsnac.details.Error
import com.keetr.comicsnac.details.Loading
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.details.Success
import com.keetr.comicsnac.details.components.DetailsErrorPlaceholder
import com.keetr.comicsnac.details.components.DetailsFlow
import com.keetr.comicsnac.details.components.DetailsLoadingPlaceholder
import com.keetr.comicsnac.details.components.DetailsScreen
import com.keetr.comicsnac.details.components.Image
import com.keetr.comicsnac.details.components.Info
import com.keetr.comicsnac.details.components.panels.enemiesPanel
import com.keetr.comicsnac.details.components.panels.friendsPanel
import com.keetr.comicsnac.details.components.panels.moviesPanel
import com.keetr.comicsnac.details.components.panels.teamEnemiesPanel
import com.keetr.comicsnac.details.components.panels.teamFriendsPanel
import com.keetr.comicsnac.details.components.panels.teamsPanel
import com.keetr.comicsnac.details.components.panels.volumesPanel
import com.keetr.comicsnac.details.components.panels.webViewPanel
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.character.CharacterDetails
import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.origin.OriginBasic
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.model.team.Team
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.ui.components.lazylist.animateScrollAndAlignItem
import com.keetr.comicsnac.ui.components.webview.toAnnotatedString
import kotlinx.coroutines.launch
import com.keetr.comicsnac.ui.R.string as CommonString

@Composable
internal fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (fullId: String) -> Unit,
    onBackPressed: () -> Unit,
    detailsUiState: CharacterDetailsUiState,
    enemies: LazyPagingItems<Character>,
    friends: LazyPagingItems<Character>,
    movies: LazyPagingItems<Movie>,
    teams: LazyPagingItems<Team>,
    teamEnemies: LazyPagingItems<Team>,
    teamFriends: LazyPagingItems<Team>,
    volumes: LazyPagingItems<Volume>
) {
    when (detailsUiState) {
        is Error -> {
            DetailsErrorPlaceholder {

            }
        }

        Loading -> {
            DetailsLoadingPlaceholder {

            }
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

            fun expandedProviderCallback(index: Int) = index == expandedIndex


            fun onExpand(index: Int) {
                scope.launch {
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
            }

            BackHandler(!canScroll) {
                imageExpanded = false
            }

            with(detailsUiState.content) {

                val body =
                    MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.tertiary)
                val title =
                    MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.tertiary)
                val headline =
                    MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.tertiary)
                val link =
                    MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)


                val annotatedString =
                    remember(body, title, headline, link) {
                        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                            .toAnnotatedString(
                                "https://comicvine.gamespot.com",
                                body,
                                title,
                                headline,
                                link
                            )
                    }

                DetailsScreen(
                    modifier = modifier,
                    images = listOf(
                        Image(
                            imageUrl, stringResource(CommonString.character_image_desc)
                        ),
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
                            if (realName.isNotBlank()) {
                                Info(name = stringResource(R.string.real_name), content = realName)
                            }
                            origin?.let {
                                Info(name = stringResource(R.string.origin), content = it.name) {
                                    onItemClicked(it.apiDetailUrl)
                                }
                            }
                            Info(name = stringResource(CommonString.gender), content = gender.name)
                            if (aliases.isNotEmpty()) {
                                Info(
                                    name = stringResource(CommonString.aliases),
                                    content = aliases.joinToString(", ")
                                )
                            }
                            Info(
                                name = stringResource(R.string.first_appeared_in_issue),
                                content = firstAppearance.name.ifBlank { "Unknown name" }
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

                    if (powers.isNotEmpty()) {
                        panelSeparator()

                        panel {
                            DetailsFlow(
                                name = stringResource(CommonString.powers), items = powers
                            ) { power ->
                                Text(power.name,
                                    Modifier
                                        .clickable { onItemClicked(apiDetailUrl) }
                                        .padding(horizontal = 8f.dp),
                                    style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }


                    if (friendsId.isNotEmpty()) {
                        panelSeparator()

                        friendsPanel(
                            friends,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (enemiesId.isNotEmpty()) {
                        panelSeparator()

                        enemiesPanel(
                            enemies,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (teamsId.isNotEmpty()) {
                        panelSeparator()

                        teamsPanel(
                            teams,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (teamFriendsId.isNotEmpty()) {
                        panelSeparator()

                        teamFriendsPanel(
                            teamFriends,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (teamEnemiesId.isNotEmpty()) {
                        panelSeparator()

                        teamEnemiesPanel(
                            teamEnemies,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (description.isNotBlank()) {
                        webViewPanel(
                            annotatedString,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    } else if (volumeCreditsId.isNotEmpty()) {
                        panelSeparator()
                    }

                    if (volumeCreditsId.isNotEmpty()) {
                        volumesPanel(
                            volumes,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (moviesId.isNotEmpty()) {
                        panelSeparator()

                        moviesPanel(
                            movies,
                            ::expandedProviderCallback,
                            ::onExpand,
                            onItemClicked
                        )
                    }

                    if (creators.isNotEmpty()) {
                        panelSeparator()

                        panel {
                            DetailsFlow(
                                name = stringResource(CommonString.creators), items = creators
                            ) { person ->
                                Text(person.name,
                                    Modifier
                                        .clickable { onItemClicked(apiDetailUrl) }
                                        .padding(horizontal = 16f.dp),
                                    style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Preview
//@Composable
//private fun Preview() {
//    ComicSnacTheme {
//
//        CharacterDetailsScreen(
//            onItemClicked = {},
//            onBackPressed = { /*TODO*/ },
//            detailsUiState = Success(Character),
//            enemies = PagingData.empty<Character>(),
//            friendsUiState = Success(Friends),
//            moviesUiState = InDevelopment,
//            teamsUiState = InDevelopment,
//            teamEnemiesUiState = InDevelopment,
//            teamFriendsUiState = InDevelopment,
//            volumeUiState = InDevelopment,
//        )
//    }
//}

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

const val SiteDetailUrl = "https://comicvine.gamespot.com/lightning-lad/4005-1253/"
internal const val SampleHtmlText =
    "<h2>Origin</h2><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/0/3664/198287-189808-lightning-lad.jpg\" data-ref-id=\"1300-198287\" data-size=\"medium\" data-ratio=\"1.2105263157895\" data-width=\"228\" data-embed-type=\"image\" style=\"width: 228px\"><a class=\"fluid-height\" style=\"padding-bottom:121.1%\" href=\"https://static.comicvine.com/uploads/original/0/3664/198287-189808-lightning-lad.jpg\" data-ref-id=\"1300-198287\"><img alt=\"Original Lightning Boy\" src=\"https://comicvine.gamespot.com/a/uploads/original/0/3664/198287-189808-lightning-lad.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/0/3664/198287-189808-lightning-lad.jpg 228w\" sizes=\"(max-width: 228px) 100vw, 228px\" data-width=\"228\"></a><figcaption>Original Lightning Boy</figcaption></figure><p>Garth Ranzz was born on the agricultural world of <a href=\"/winath/4020-56915/\" data-ref-id=\"4020-56915\">Winath</a> with his twin, Ayla. Garth, <a href=\"../../light-lass/29-30997/\" rel=\"nofollow \">Ayla</a>, and their older brother <a href=\"../../lightning-lord/29-3592/\" rel=\"nofollow \">Mekt</a> were joy riding when their ship lost power and came down on the world of <a href=\"/korbal/4020-56256/\" data-ref-id=\"4020-56256\">Korbal</a>. They devised a scheme to recharge their ship's energy cells using the electrical energy of Korbal's lightning beasts. They underestimated the beasts' power and were all bathed in bio-electric energy. After their return with new found powers, Mekt disappeared. Garth left home to look for his brother. On his journey he helped two other super powered teenagers foil the assassination of philanthropist <a href=\"../../rj-brande/29-12810/\" rel=\"nofollow \">R.J. Brande</a>. Brande suggested they form a team - the <a href=\"../../legion-of-super-heroes/65-19241/\" rel=\"nofollow \">Legion of Super-Heroes</a>. Garth assumed the name Lightning Boy initially, but switched to Lightning Lad.</p><h2>Creation</h2><p>Lightning Lad was created by <a href=\"../../otto-binder/26-42374/\" rel=\"nofollow \">Otto Binder</a> and <a href=\"../../al-plastino/26-39933/\" rel=\"nofollow \">Al Plastino</a></p><h2>Character Evolution</h2><h3><b>Original Continuity: New Earth</b></h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/2/25838/489302-lightning_lad1.png\" data-ref-id=\"1300-489302\" data-size=\"medium\" data-ratio=\"1.6961130742049\" data-width=\"283\" data-embed-type=\"image\" style=\"width: 283px\"><a class=\"fluid-height\" style=\"padding-bottom:169.6%\" href=\"https://static.comicvine.com/uploads/original/2/25838/489302-lightning_lad1.png\" data-ref-id=\"1300-489302\"><img alt=\"Lightning Lad (New Earth)\" src=\"https://comicvine.gamespot.com/a/bundles/phoenixsite/images/core/loose/img_broken.png\" srcset=\"https://comicvine.gamespot.com/a/bundles/phoenixsite/images/core/loose/img_broken.png 283w\" sizes=\"(max-width: 283px) 100vw, 283px\" data-width=\"283\"></a><figcaption>Lightning Lad (New Earth)</figcaption></figure><p>He was one of the first Legionnaires to be killed when he sacrificed his life to save <a href=\"/saturn-girl/4005-1273/\" data-ref-id=\"4005-1273\">Saturn Girl</a>, taking a blast from <a href=\"/zaryan-the-conqueror/4005-72707/\" data-ref-id=\"4005-72707\">Zaryan the Conqueror</a>'s ship. He was resurrected by <a href=\"../../proty/29-12861/\" rel=\"nofollow \">Proty</a>, the shape shifting pet of <a href=\"/chameleon-boy/4005-5945/\" data-ref-id=\"4005-5945\">Chameleon Boy</a>, who sacrificed his life to revive Garth in a lightning ceremony. Later, Garth lost his right arm to a giant space whale, and had to get a cybernetic replacement. Years later, his parents were killed in a space cruiser accident. His years on the Legion weren't all bad. He formed close friendships with <a href=\"/cosmic-boy/4005-1264/\" data-ref-id=\"4005-1264\">Cosmic Boy</a> and <a href=\"/sun-boy/4005-1259/\" data-ref-id=\"4005-1259\">Sun Boy</a> and he and Saturn Girl became one of the Legion's earliest couples following his resurrection. His sister joined the Legion as well. Though Garth lost his arm, it was eventually restored. After several years of dating, Lightning Lad proposed to Saturn Girl and she accepted.</p><h3>Reboot: Earth-247</h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/0/760/81237-155819-lightning-lad.jpg\" data-ref-id=\"1300-81237\" data-size=\"medium\" data-ratio=\"2.1111111111111\" data-width=\"180\" data-embed-type=\"image\" style=\"width: 180px\"><a class=\"fluid-height\" style=\"padding-bottom:211.1%\" href=\"https://static.comicvine.com/uploads/original/0/760/81237-155819-lightning-lad.jpg\" data-ref-id=\"1300-81237\"><img alt=\"Live Wire (Earth-247)\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 180px) 100vw, 180px\" data-width=\"180\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/0/760/81237-155819-lightning-lad.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/0/760/81237-155819-lightning-lad.jpg 180w\"><noscript><img alt=\"Live Wire (Earth-247)\" src=\"https://comicvine.gamespot.com/a/uploads/original/0/760/81237-155819-lightning-lad.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/0/760/81237-155819-lightning-lad.jpg 180w\" sizes=\"(max-width: 180px) 100vw, 180px\" data-width=\"180\"></noscript></a><figcaption>Live Wire (Earth-247)</figcaption></figure><p>In his search for his brother Mekt he had been branded a runaway due to only being 14, which was a minor on Winath. When his sister arrived to join the Legion as Winath's official representative, Garth quit in shame. He went on to work for the industrialist <a href=\"/leland-mccauley/4005-12846/\" data-ref-id=\"4005-12846\">Leland McCauley</a> on his <a href=\"/workforce/4060-47189/\" data-ref-id=\"4060-47189\">Workforce</a> team. Garth had hoped to make some money to look for his brother, but quit the team soon after due to McCauley's lack of morals. Despite aiding the Legion against Daxamite terrorists, Garth was still not allowed to rejoin, so he went off again to find his brother. When he found Mekt, his mind had snapped, and he was a deadly criminal. Mekt abducted Garth and shot his right arm off. After recovering from his battle with Mekt and gaining a cybernetic arm, Garth worked with a newly formed Espionage Squad to uncover the corruption of <a href=\"/president-chu/4005-21136/\" data-ref-id=\"4005-21136\">President Chu</a>. She was impeached, and Brande was made president in her place. His first act as President was to eliminate the membership restrictions on the Legion, finally allowing Garth to rejoin.</p><h3>Threeboot: Earth-Prime</h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/1/15696/322174-37608-lightning-lad.jpg\" data-ref-id=\"1300-322174\" data-size=\"medium\" data-ratio=\"1.1575\" data-width=\"400\" data-embed-type=\"image\" style=\"width: 400px\"><a class=\"fluid-height\" style=\"padding-bottom:115.8%\" href=\"https://static.comicvine.com/uploads/original/1/15696/322174-37608-lightning-lad.jpg\" data-ref-id=\"1300-322174\"><img alt=\"Lightning Lad (Earth-Prime)\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 400px) 100vw, 400px\" data-width=\"400\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/1/15696/322174-37608-lightning-lad.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/1/15696/322174-37608-lightning-lad.jpg 400w\"><noscript><img alt=\"Lightning Lad (Earth-Prime)\" src=\"https://comicvine.gamespot.com/a/uploads/original/1/15696/322174-37608-lightning-lad.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/1/15696/322174-37608-lightning-lad.jpg 400w\" sizes=\"(max-width: 400px) 100vw, 400px\" data-width=\"400\"></noscript></a><figcaption>Lightning Lad (Earth-Prime)</figcaption></figure><p>On Earth-Prime, Garth Ranzz was Lightning Lad once more and one of the founders of the Legion of Super-Heroes. They were now a teen youth movement and Garth coined their first battle cry: \"Eat it, Grandpa!\" He was dating Saturn Girl, and said that the only way to successfully date a telepath was to be \"way honest.\" He was instrumental in gaining legal status for the Legion, but forgot to read the fine print in the agreement - the U.P. now had the right to use the Legion's image in any way they wished. When Cosmic Boy disappeared after the Dominators attempted to take over Earth, a public election was held for Legion leadership. <a href=\"/supergirl/4005-2351/\" data-ref-id=\"4005-2351\">Supergirl</a> was elected with Lightning Lad her deputy. She soon left for the 21st century and Garth became the team leader. Garth was quickly overwhelmed with the responsibilities of leadership, especially dealing with the various organizations of the United Planets. This put a strain in he and Saturn Girl's relationship which led her to have a psychic affair. She confessed the indiscretion and Garth broke up with her.</p><h3>Retroboot: New Earth</h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/3/36257/1093603-lightninglad_bycinar.jpg\" data-ref-id=\"1300-1093603\" data-size=\"medium\" data-ratio=\"1.4466257668712\" data-width=\"815\" data-embed-type=\"image\" style=\"width: 815px\"><a class=\"fluid-height\" style=\"padding-bottom:144.7%\" href=\"https://static.comicvine.com/uploads/original/3/36257/1093603-lightninglad_bycinar.jpg\" data-ref-id=\"1300-1093603\"><img alt=\"Lightning Lad (New Earth)\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 480px) 100vw, 480px\" data-width=\"480\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/scale_medium/3/36257/1093603-lightninglad_bycinar.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/3/36257/1093603-lightninglad_bycinar.jpg 815w, https://comicvine.gamespot.com/a/uploads/scale_medium/3/36257/1093603-lightninglad_bycinar.jpg 480w\"><noscript><img alt=\"Lightning Lad (New Earth)\" src=\"https://comicvine.gamespot.com/a/uploads/scale_medium/3/36257/1093603-lightninglad_bycinar.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/3/36257/1093603-lightninglad_bycinar.jpg 815w, https://comicvine.gamespot.com/a/uploads/scale_medium/3/36257/1093603-lightninglad_bycinar.jpg 480w\" sizes=\"(max-width: 480px) 100vw, 480px\" data-width=\"480\"></noscript></a><figcaption>Lightning Lad (New Earth)</figcaption></figure><p><a href=\"/infinite-crisis/4045-40765/\" data-ref-id=\"4045-40765\">Infinite Crisis</a> restored the original Legion continuity from before Five Years Later. Garth was once again married to Saturn Girl and a father to twin sons, though he ultimately rejoined the Legion when <a href=\"/earth-man/4005-32576/\" data-ref-id=\"4005-32576\">Earth-Man</a> began an anti-alien campaign on Earth. The Legion founders went to the <a href=\"/batcave/4020-31340/\" data-ref-id=\"4020-31340\">Batcave</a> to find proof that <a href=\"/superman/4005-1807/\" data-ref-id=\"4005-1807\">Superman</a> was actually a Kryptonian and not an Earthling like Earth-Man was leading the people to believe, but Garth and the others were captured. Eventually he was freed and Earth-Man was defeated. After Saturn Girl's homeworld of Titan was destroyed and their twin boys were kidnapped but recovered from a servant of Darkseid, Garth and Imra decide to take a leave of absence from the Legion to spend time with their boys.</p><h3>Post-Flashpoint: Earth-0</h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/10/108841/2226596-2148227-2148212-a_21.jpg\" data-ref-id=\"1300-2226596\" data-size=\"medium\" data-ratio=\"2.5765765765766\" data-width=\"555\" data-embed-type=\"image\" style=\"width: 555px\"><a class=\"fluid-height\" style=\"padding-bottom:257.7%\" href=\"https://static.comicvine.com/uploads/original/10/108841/2226596-2148227-2148212-a_21.jpg\" data-ref-id=\"1300-2226596\"><img alt=\"Lightning Man (Earth-0)\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 480px) 100vw, 480px\" data-width=\"480\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/scale_medium/10/108841/2226596-2148227-2148212-a_21.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/10/108841/2226596-2148227-2148212-a_21.jpg 555w, https://comicvine.gamespot.com/a/uploads/scale_medium/10/108841/2226596-2148227-2148212-a_21.jpg 480w\"><noscript><img alt=\"Lightning Man (Earth-0)\" src=\"https://comicvine.gamespot.com/a/uploads/scale_medium/10/108841/2226596-2148227-2148212-a_21.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/10/108841/2226596-2148227-2148212-a_21.jpg 555w, https://comicvine.gamespot.com/a/uploads/scale_medium/10/108841/2226596-2148227-2148212-a_21.jpg 480w\" sizes=\"(max-width: 480px) 100vw, 480px\" data-width=\"480\"></noscript></a><figcaption>Lightning Man (Earth-0)</figcaption></figure><p>Following the conclusion of <a href=\"../../flashpoint/39-56280/\" rel=\"nofollow \">Flashpoint</a>, the various timelines were united and rebooted once again. The Legion's timeline changed slightly where they still reached out to a young Clark Kent long before he became Superman, but they Legion grew up along with Clark and have changed their names to reflect their age. Imra changed her name to Saturn Woman and her along with Saturn Girl (Now Saturn Woman) and Cosmic Boy (Now Cosmic Man) reunited with an adult Clark Kent in an effort to stop the <a href=\"../../anti-superman-army/65-58873/\" rel=\"nofollow \">Anti-Superman army</a> from destroying the original rocket that took Clark to Earth. They succeeded in saving the rocket and keeping he timeline intact. During the battle, Garth's right arm was apparently destroyed off-panel. The wires seen in its place indicate that he had already replaced it with a cybernetic arm akin to the Earth-247 version.</p><p> </p><p> </p><p> </p><p> </p><p> </p><p> </p><p> </p><p> </p><p><strong>DC Rebirth</strong></p><p>Lightning Lad reappears once again as a founding member of the Legion Of Superheroes</p><p> </p><h2>Major Story Arcs</h2><h3><b>Original Continuity: New Earth</b></h3><h4>Married Life</h4><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/2/25838/489315-lightning_lad_saturn_girlgarthimra.jpg\" data-ref-id=\"1300-489315\" data-size=\"medium\" data-ratio=\"1.0098684210526\" data-width=\"304\" data-embed-type=\"image\" style=\"width: 304px\"><a class=\"fluid-height\" style=\"padding-bottom:101.0%\" href=\"https://static.comicvine.com/uploads/original/2/25838/489315-lightning_lad_saturn_girlgarthimra.jpg\" data-ref-id=\"1300-489315\"><img alt=\"Garth x Imra\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 304px) 100vw, 304px\" data-width=\"304\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/2/25838/489315-lightning_lad_saturn_girlgarthimra.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/25838/489315-lightning_lad_saturn_girlgarthimra.jpg 304w\"><noscript><img alt=\"Garth x Imra\" src=\"https://comicvine.gamespot.com/a/uploads/original/2/25838/489315-lightning_lad_saturn_girlgarthimra.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/25838/489315-lightning_lad_saturn_girlgarthimra.jpg 304w\" sizes=\"(max-width: 304px) 100vw, 304px\" data-width=\"304\"></noscript></a><figcaption>Garth x Imra</figcaption></figure><p>The duo were married and were forced to leave the Legion by its own by-laws, but this was shortly thereafter vetoed and the couple returned to active duty. Garth was even elected leader at one point, but the stress became too much and he resigned before the end of his term. Imra became pregnant and gave birth to a healthy baby boy, <a href=\"../../graym-ranzz/29-52820/\" rel=\"nofollow \">Graym Ranzz</a>. Wanting to spend more time with his young family, Garth resigned with his fellow Legion founders from the team. Though it was a horror to learn that the villainous <a href=\"../../validus/29-21241/\" rel=\"nofollow \">Validus</a> was actually his son's twin, thrust through time and manipulated by <a href=\"../../darkseid/29-2349/\" rel=\"nofollow \">Darkseid</a>, the boy was eventually restored and returned to the Ranzzes. Even after Saturn Girl returned to active duty, Garth remained at home with his sons.</p><h4>Five Years Later</h4><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/8/80419/1892563-lil41.jpg\" data-ref-id=\"1300-1892563\" data-size=\"medium\" data-ratio=\"1.672619047619\" data-width=\"168\" data-embed-type=\"image\" style=\"width: 168px\"><a class=\"fluid-height\" style=\"padding-bottom:167.3%\" href=\"https://static.comicvine.com/uploads/original/8/80419/1892563-lil41.jpg\" data-ref-id=\"1300-1892563\"><img alt=\"Garth Ranzz (Five Years Later)\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 168px) 100vw, 168px\" data-width=\"168\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/8/80419/1892563-lil41.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/8/80419/1892563-lil41.jpg 168w\"><noscript><img alt=\"Garth Ranzz (Five Years Later)\" src=\"https://comicvine.gamespot.com/a/uploads/original/8/80419/1892563-lil41.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/8/80419/1892563-lil41.jpg 168w\" sizes=\"(max-width: 168px) 100vw, 168px\" data-width=\"168\"></noscript></a><figcaption>Garth Ranzz (Five Years Later)</figcaption></figure><p>Following the five years later after the <a href=\"../../the-magic-wars/39-56080/\" rel=\"nofollow \">Magic Wars</a>, Garth had relocated his family to Winath. There they established the Lightning Plantation and became a successful farming and shipping collective. Tragedy struck when Garth's son <a href=\"../../validus/29-21241/\" rel=\"nofollow \">Garridan</a>, the former Validus, began infecting Winathians with a deadly plague. His son had to be taken off-world to the planet Quarantine and could only visit wearing a special suit. The disease also caused Garth to walk with a limp. However, good news soon followed. Imra became pregnant again with twins and the Legion of Super-Heroes was reforming. Though the Ranzzes didn't join back up, they did assist their old teammates from time to time and Garth even gave them an old storage facility on <a href=\"../../talus/34-57116/\" rel=\"nofollow \">Talus</a> to use as a headquarters.</p><h4>Who is Gath?</h4><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/10/108841/2227711-1480041-200px_proty.jpg\" data-ref-id=\"1300-2227711\" data-size=\"medium\" data-ratio=\"1.0522388059701\" data-width=\"134\" data-embed-type=\"image\" style=\"width: 134px\"><a class=\"fluid-height\" style=\"padding-bottom:105.2%\" href=\"https://static.comicvine.com/uploads/original/10/108841/2227711-1480041-200px_proty.jpg\" data-ref-id=\"1300-2227711\"><img alt=\"The mind of Proty\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 134px) 100vw, 134px\" data-width=\"134\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/10/108841/2227711-1480041-200px_proty.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/10/108841/2227711-1480041-200px_proty.jpg 134w\"><noscript><img alt=\"The mind of Proty\" src=\"https://comicvine.gamespot.com/a/uploads/original/10/108841/2227711-1480041-200px_proty.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/10/108841/2227711-1480041-200px_proty.jpg 134w\" sizes=\"(max-width: 134px) 100vw, 134px\" data-width=\"134\"></noscript></a><figcaption>The mind of Proty</figcaption></figure><p>Shortly after his daughters Dacey and Dorritt were born, his sister Ayla stumbled upon a secret that Garth had been keeping: he wasn't Garth at all, but Proty. Proty's consciousness had been transferred into Garth's body and was the cause of Lightning Lad's resurrection. Being a former telepath as Proty, Garth was able to protect his thoughts from his telepathic wife about the matter. Garth assisted Legionnaires past and present in hunting down <a href=\"../../glorith/29-26385/\" rel=\"nofollow \">Glorith</a> and <a href=\"../../mordru/29-4904/\" rel=\"nofollow \">Mordru</a> to save Cosmic Boy. <a href=\"../../zero-hour/39-40782/\" rel=\"nofollow \">Zero Hour</a> then happened and Garth and the other Legionnaires sacrificed themselves to save the timestream and create a new reality.</p><h3>Reboot: Earth-247</h3><h4>The Death of Garth</h4><p>When half the Legion was stuck in the 20th century, Brande personally asked Garth to become Legion leader. Live Wire wasn't comfortable with the idea and shortly thereafter hosted a leadership election for a replacement. Once the team was reunited, he began a relationship with Saturn Girl and ultimately proposed. Garth and Imra were one of several Legionnaires lost in a spatial rift. Saturn Girl's manipulation of the team put a strain on their relationship. Live Wire sacrificed himself fighting their former teammate, <a href=\"../../element-lad/29-1263/\" rel=\"nofollow \">Element Lad</a>, who had spent billions of years becoming a mad god.</p><h4>Garth's Body Restored</h4><p>Garth's spirit, however, was stored in the living crystals of <a href=\"../../element-lad/29-1263/\" rel=\"nofollow \">Element Lad</a>'s corpse, and he was able to return to the Legion - but in Element Lad's body. This caused a lot of anxiety among the Legion, but eventually his friends came to accept Garth. When Earth-247 was destroyed in <a href=\"../../infinite-crisis/39-40765/\" rel=\"nofollow \">Infinite Crisis</a>, Garth and his team survived because they had been lost in the timestream. Garth's original body was eventually restored when the <a href=\"../../brainiac-5/29-1255/\" rel=\"nofollow \">Brainiac 5</a> of New Earth used a special lightning rod to help Garth transmute himself to normal. He then rejoined his team as they pushed into the <a href=\"../../dc-multiverse/12-43603/\" rel=\"nofollow \">multiverse</a> as the new <a href=\"../../wanderers/65-47418/\" rel=\"nofollow \">Wanderers</a>.</p><h2>Powers &amp; Abilities</h2><h3>Electricity Control</h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/2/26503/506307-sensor26.jpg\" data-ref-id=\"1300-506307\" data-size=\"medium\" data-ratio=\"1.3312693498452\" data-width=\"646\" data-embed-type=\"image\" style=\"width: 646px\"><a class=\"fluid-height\" style=\"padding-bottom:133.1%\" href=\"https://static.comicvine.com/uploads/original/2/26503/506307-sensor26.jpg\" data-ref-id=\"1300-506307\"><img alt=\"Lightning Powers\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 480px) 100vw, 480px\" data-width=\"480\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/scale_medium/2/26503/506307-sensor26.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/506307-sensor26.jpg 646w, https://comicvine.gamespot.com/a/uploads/scale_medium/2/26503/506307-sensor26.jpg 480w\"><noscript><img alt=\"Lightning Powers\" src=\"https://comicvine.gamespot.com/a/uploads/scale_medium/2/26503/506307-sensor26.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/506307-sensor26.jpg 646w, https://comicvine.gamespot.com/a/uploads/scale_medium/2/26503/506307-sensor26.jpg 480w\" sizes=\"(max-width: 480px) 100vw, 480px\" data-width=\"480\"></noscript></a><figcaption>Lightning Powers</figcaption></figure><p>Lightning Lad has the ability to generate electricity and direct bolts of electricity accurately. Lightning Lad can use his power destructively, such as to short-circuit electrical items, split boulders, burn objects with precision or shatter walls. He can also reduce the force of his bolts so that they will only stun. He can send his electricity through conductive metals. He has a degree of immunity to electrical charge; in fact, they give him more strength to use his power. In some instances he has a robotic right arm that is powered by his lightning power.</p><h3>Equipment</h3><p>As a member of the Legion of Super-Heroes, Lightning Lad possesses a <a href=\"../../legion-flight-ring/18-47353/\" rel=\"nofollow \">Legion Flight Ring</a>. The ring gives its wearer the ability to fly, the speed and range of which is determined by the wearer's willpower. It also acts as a long-range communicator (enabling constant vocal contact with other Legionnaires, even across vast distances of space), a signal device, and a navigational compass, all powered by a micro-computer built inside the ring.</p><h2>Other Versions</h2><h3>Silver Age: Lightning Man</h3><p>In the original debut of the Legion of Supervillians, this team came from a time when the Legion of Superheroes had reach adulthood. Among the members it also was Lightning Man, an adult version of Garth. Superboy and later Superman would meet several times this possible future for the Legion. The concept would be revisited after Flashpoint. (<em>Note:This Lightning man must not to be confused with an alternate identity of Superman, also used in the Silver Age</em>).</p><h3>Five Years Later / Batch SW6</h3><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/0/3664/196791-77896-lightning-lad.jpg\" data-ref-id=\"1300-196791\" data-size=\"small\" data-ratio=\"1.5906040268456\" data-width=\"298\" data-embed-type=\"image\" style=\"width: 298px\"><a class=\"fluid-height\" style=\"padding-bottom:159.1%\" href=\"https://static.comicvine.com/uploads/original/0/3664/196791-77896-lightning-lad.jpg\" data-ref-id=\"1300-196791\"><img alt=\"Live Wire (Batch SW6)\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 298px) 100vw, 298px\" data-width=\"298\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/0/3664/196791-77896-lightning-lad.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/0/3664/196791-77896-lightning-lad.jpg 298w\"><noscript><img alt=\"Live Wire (Batch SW6)\" src=\"https://comicvine.gamespot.com/a/uploads/original/0/3664/196791-77896-lightning-lad.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/0/3664/196791-77896-lightning-lad.jpg 298w\" sizes=\"(max-width: 298px) 100vw, 298px\" data-width=\"298\"></noscript></a><figcaption>Live Wire (Batch SW6)</figcaption></figure><p>Months beforehand, young counterparts from the Legion's early days surfaced among <a href=\"../../dominators/65-56539/\" rel=\"nofollow \">Dominator</a> experiments on Earth. A young Lightning Lad, complete with restored arm, was among their number and had the hotshot personality of the original Garth. His team went on to become the Legionnaires of New Earth and Garth took on the name Live Wire. Live Wire ultimately merged with the elder Lightning Lad during Zero Hour.</p><h2>Other Media</h2><h3>Television</h3><h4>DC Animated Universe</h4><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/2/26503/534410-lightning_lad.jpg\" data-ref-id=\"1300-534410\" data-size=\"medium\" data-ratio=\"0.75\" data-width=\"400\" data-embed-type=\"image\" style=\"width: 400px\"><a class=\"fluid-height\" style=\"padding-bottom:75.0%\" href=\"https://static.comicvine.com/uploads/original/2/26503/534410-lightning_lad.jpg\" data-ref-id=\"1300-534410\"><img alt=\"Lightning Lad on JLU\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 400px) 100vw, 400px\" data-width=\"400\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/534410-lightning_lad.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/534410-lightning_lad.jpg 400w\"><noscript><img alt=\"Lightning Lad on JLU\" src=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/534410-lightning_lad.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/534410-lightning_lad.jpg 400w\" sizes=\"(max-width: 400px) 100vw, 400px\" data-width=\"400\"></noscript></a><figcaption>Lightning Lad on JLU</figcaption></figure><p>Lightning Lad had small, non-speaking roles in the Legion's appearances on both <i>Superman: the Animated Series</i> and <i>Justice League Unlimited</i><i>.</i></p><p>The <a href=\"/superman/4005-1807/\" data-ref-id=\"4005-1807\">Superman</a> episode \"New Kids in Town\" featured him as one of several Legionnaires back in the 30th century. The JLU episode \"Far From Home\" featured Garth as one of many Legionnaires possessed by the <a href=\"/emerald-eye-of-ekron/4055-48914/\" data-ref-id=\"4055-48914\">Emerald Eye</a> and willing to attack Supergirl. He, along with the rest of the Legion, was ultimately freed.</p><h4>Superman &amp; The Legion of Super-Heroes</h4><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/2/26503/502416-200px_lightning_lad___dcau_04.jpg\" data-ref-id=\"1300-502416\" data-size=\"medium\" data-ratio=\"1.246835443038\" data-width=\"316\" data-embed-type=\"image\" style=\"width: 316px\"><a class=\"fluid-height\" style=\"padding-bottom:124.7%\" href=\"https://static.comicvine.com/uploads/original/2/26503/502416-200px_lightning_lad___dcau_04.jpg\" data-ref-id=\"1300-502416\"><img alt=\"Lightning Lad on Legion\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 316px) 100vw, 316px\" data-width=\"316\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/502416-200px_lightning_lad___dcau_04.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/502416-200px_lightning_lad___dcau_04.jpg 316w\"><noscript><img alt=\"Lightning Lad on Legion\" src=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/502416-200px_lightning_lad___dcau_04.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/502416-200px_lightning_lad___dcau_04.jpg 316w\" sizes=\"(max-width: 316px) 100vw, 316px\" data-width=\"316\"></noscript></a><figcaption>Lightning Lad on Legion</figcaption></figure><p>Lightning Lad was a main character on the two seasons of the animated Legion of Super-Heroes show. He had an origin similar to the comic books, though this time his sister Ayla didn't survive the attack by the lightning beasts as children. This same event also left Garth with a lightning bolt scar over one eye, which lit up when he used his powers. During the two seasons of the show, Garth had a flirtation with Saturn Girl but never properly developed a romance. He often butted heads with fellow co-founder Cosmic Boy on issues and Saturn Girl specifically. He appeared in every episode of season one and all but \"Cry Wolf\", \"Unnatural Alliances\", and \"Trials\" in season two.</p><p>In season one, Garth joined the Legion of Super-Villains to be with his brother Mekt and leave behind his immature Legion teammates but quickly learned the team wasn't for him. In season two, Garth lost his arm to his brother and <a href=\"/imperiex/4005-10006/\" data-ref-id=\"4005-10006\">Imperiex</a> and was given a cybernetic one. On the same adventure, Garth was able to reconstitute his sister, who had been existing as a lightning cloud since her disappearance.</p><h4>Smallville</h4><figure data-align=\"right\" data-img-src=\"https://static.comicvine.com/uploads/original/2/26503/759249-lightninglad1.jpg\" data-ref-id=\"1300-759249\" data-size=\"medium\" data-ratio=\"1.3157894736842\" data-width=\"380\" data-embed-type=\"image\" style=\"width: 380px\"><a class=\"fluid-height\" style=\"padding-bottom:131.6%\" href=\"https://static.comicvine.com/uploads/original/2/26503/759249-lightninglad1.jpg\" data-ref-id=\"1300-759249\"><img alt=\"Lightning Lad on Smallville\" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" sizes=\"(max-width: 380px) 100vw, 380px\" data-width=\"380\" class=\"js-lazy-load-image\" data-src=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/759249-lightninglad1.jpg\" data-srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/759249-lightninglad1.jpg 380w\"><noscript><img alt=\"Lightning Lad on Smallville\" src=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/759249-lightninglad1.jpg\" srcset=\"https://comicvine.gamespot.com/a/uploads/original/2/26503/759249-lightninglad1.jpg 380w\" sizes=\"(max-width: 380px) 100vw, 380px\" data-width=\"380\"></noscript></a><figcaption>Lightning Lad on Smallville</figcaption></figure><p>Garth was one of the founding Legionnaires who traveled to 20th century Smallville in the same-titled show, episode called \"Legion.\" He acted and appeared to be the youngest of the founding Legionnaires. Lightning Lad was there alongside Cosmic Boy and Saturn Girl to ward off the <a href=\"/persuader/4005-5940/\" data-ref-id=\"4005-5940\">Persuader</a> and help stop Brainiac by any means necessary, including killing Brainiac's host. Clark taught the Legionnaires that killing was never the answer and they decide to adopt a no killing policy in their Legion Constitution upon successfully capturing Brainiac without injuring its host. Garth and the others then returned to the future. Garth was portrayed by Calum Worthy.</p>"


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
    description = SampleHtmlText,
    firstAppearance = IssueBasic(
        apiDetailUrl = "http://www.bing.com/search?q=mucius", id = 4695, name = "Cecilia Blevins"
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
            apiDetailUrl = "https://duckduckgo.com/?q=euripidis", id = 5718, name = "Allyson Barnes"
        )
    },
    publisher = null,
    realName = "Lance Walsh",
    siteDetailUrl = SiteDetailUrl,
    teamEnemiesId = listOf(),
    teamFriendsId = listOf(),
    teamsId = listOf(),
    volumeCreditsId = listOf()
)
