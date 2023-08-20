package com.keetr.comicsnac.details

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.components.lazylist.ComicListSeparator
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.theme.ComicSnacTheme


@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    images: List<Image>,
    onBackPressed: () -> Unit,
    userScrollEnabled: Boolean = true,
    imageExpanded: Boolean,
    onImageClicked: () -> Unit,
    onImageClose: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    content: PanelLazyListScope.() -> Unit
) {
    Scaffold(
        modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        Box {
            val panelColors = with(MaterialTheme.colorScheme) {
                PanelColors(
                    strokeColor = outline,
                    surface1 = secondary,
                    surface2 = primary,
                    surface3 = tertiary
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
                    )

                    Box(
                        Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                if (imageExpanded) onImageClose() else onBackPressed()
                            }
                            .size(64f.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedContent(
                            targetState = imageExpanded,
                            transitionSpec = {
                                scaleIn() togetherWith scaleOut()
                            },
                            label = "carousel_image"
                        ) { imageExpanded ->
                            if (imageExpanded) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            } else {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = imageExpanded,
                        enter = expandIn(expandFrom = Alignment.Center),
                        exit = shrinkOut(shrinkTowards = Alignment.Center),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable { }
                            .size(64f.dp)
                    ) {
                        Box(
                            Modifier
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
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
}


fun PanelLazyListScope.webViewPanel(
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
        var expanded by remember {
            mutableStateOf(false)
        }
        DetailsScreen(
            images = List(5) { Image(it.toString(), null) },
            imageExpanded = expanded,
            onImageClicked = { expanded = true },
            onBackPressed = {
                expanded = false
            },
            onImageClose = { }
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
            webViewPanel {
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