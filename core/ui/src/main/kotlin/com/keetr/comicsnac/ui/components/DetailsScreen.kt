package com.keetr.comicsnac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.keetr.comicsnac.ui.components.cards.PlainCard
import com.keetr.comicsnac.ui.components.lazylist.ComicListSeparator
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    imageUrl: List<String>,
    content: PanelLazyListScope.(LazyListScope) -> Unit
) {
    Scaffold(
        modifier, containerColor = Color(0xFF050A0C)
    ) { paddingValues ->
        val panelColors = with(MaterialTheme.colorScheme) {
            PanelColors(
                strokeColor = outline, surface1 = primary, surface2 = tertiary, surface3 = secondary
            )
        }
        val lazyListState = rememberLazyListState()
        LazyColumn(
            contentPadding = paddingValues, state = lazyListState
        ) {
            item ("image") {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(0.75f)
                ) {
                    PlainCard(
                        Modifier
                            .graphicsLayer {
                                if (lazyListState.firstVisibleItemIndex == 0) {
                                    scaleX = lerp(
                                        0.2f,
                                        1.0f,
                                        1f - (lazyListState.firstVisibleItemScrollOffset.toFloat() / lazyListState.layoutInfo.viewportEndOffset)
                                    )
                                }
                            }
                            .fillMaxSize(0.7f)
                            .aspectRatio(11f / 17f)
                            .align(Alignment.Center)
                            .shadow(
                                elevation = 16f.dp,
                                ambientColor = MaterialTheme.colorScheme.onSurface,
                                spotColor = MaterialTheme.colorScheme.onSurface
                            ), backgroundColor = Color.Gray
                    ) {

                    }
                }
            }
            item("top_separator") {
                ComicListSeparator(
                    upperColor = Color.Transparent,
                    lowerColor = MaterialTheme.colorScheme.primary,
                    strokeColor = MaterialTheme.colorScheme.onSurface,
                    flipped = false
                )
            }
//            panelList(panelColors) {
//                content(this@LazyColumn)
//            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        DetailsScreen(imageUrl = listOf("")) {
            with(it) {
                repeat(1) {
                    panel {
                        Box(
                            Modifier
                                .padding(24f.dp)
                                .background(Color.Red)
                                .fillMaxWidth()
                                .height(120f.dp)
                        )
                    }
                }
                item("desc_top_separator") {
                    ComicListSeparator(
                        upperColor = Color.Red,
                        lowerColor = Color.Magenta,
                        strokeColor = MaterialTheme.colorScheme.onSurface,
                        flipped = false
                    )
                }
            }
        }
    }
}