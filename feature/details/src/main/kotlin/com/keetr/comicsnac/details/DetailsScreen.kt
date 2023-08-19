package com.keetr.comicsnac.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.components.lazylist.ComicListSeparator
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.theme.ComicSnacTheme


data class DetailsScreenState(
    var imageExpanded: Boolean,
    var expandedPanelIndex: Int,
)

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    images: List<Image>,
    onBackPressed: () -> Unit,
    userScrollEnabled: Boolean = true,
    imageExpanded: Boolean,
    onImageClicked: () -> Unit,
    onImageCloseClicked: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    content: PanelLazyListScope.() -> Unit
) {
    Scaffold(
        modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        val panelColors = with(MaterialTheme.colorScheme) {
            PanelColors(
                strokeColor = outline, surface1 = secondary, surface2 = primary, surface3 = tertiary
            )
        }
        PanelList(
            contentPadding = paddingValues,
            state = lazyListState,
            colors = panelColors,
            userScrollEnabled = userScrollEnabled
        ) {
            panel(true) {
                ImageCarousel(
                    images = images,
                    lazyListState = lazyListState,
                    imageExpanded = imageExpanded,
                    onImageClicked = onImageClicked,
                    onBackPressed =  onImageCloseClicked,
                )
            }
            panelSeparator { _, lowerColor, strokeColor, flipped ->
                ComicListSeparator(
                    upperColor = Color.Transparent,
                    lowerColor = lowerColor,
                    strokeColor = strokeColor,
                    flipped = flipped
                )
            }
            content()
        }
    }
}


fun PanelLazyListScope.descriptionPanel(
    content: @Composable LazyItemScope.() -> Unit,
) {
    panelSeparator { upperColor, lowerColor, _, flipped ->

        ComicListSeparator(
            upperColor = upperColor,
            lowerColor = MaterialTheme.colorScheme.onSurface,
            strokeColor = lowerColor,
            flipped = flipped
        )
    }
    panel {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.onSurface)
        ) {
            content()
        }
    }
    panelSeparator { upperColor, lowerColor, _, flipped ->
        ComicListSeparator(
            upperColor = MaterialTheme.colorScheme.onSurface,
            lowerColor = lowerColor,
            strokeColor = upperColor,
            flipped = flipped
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        DetailsScreen(
            images = List(5) { Image(it.toString(), null) },
            imageExpanded = true,
            onImageClicked = { },
            onImageCloseClicked = { },
            onBackPressed = {}
        ) {
            repeat(4) {
                panel {
                    Box(
                        Modifier
                            .padding(24f.dp)
                            .background(Color.Red)
                            .fillMaxWidth()
                            .height(120f.dp)
                    )
                }
                panelSeparator()
            }

            panel {
                Box(
                    Modifier
                        .padding(24f.dp)
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(120f.dp)
                )
            }
            descriptionPanel {
                Box(
                    Modifier
                        .padding(24f.dp)
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(120f.dp)
                )
            }
        }
    }
}