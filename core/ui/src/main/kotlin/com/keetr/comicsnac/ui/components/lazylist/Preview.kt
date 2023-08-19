package com.keetr.comicsnac.ui.components.lazylist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlinx.coroutines.launch

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        val primary = MaterialTheme.colorScheme.primary
        val secondary = MaterialTheme.colorScheme.secondary
        val tertiary = MaterialTheme.colorScheme.tertiary
        val strokeColor = MaterialTheme.colorScheme.onSurface

        val state = rememberLazyListState()
        val scope = rememberCoroutineScope()
        var expandedIndex by remember {
            mutableIntStateOf(-1)
        }


        PanelList(
            Modifier.fillMaxSize(),
            PanelColors(strokeColor, primary, secondary, tertiary),
            state = state, userScrollEnabled = expandedIndex < 0
        ) {
            repeat(30) { index ->

                panel {
                    val expandedModifier =
                        if (index == expandedIndex) Modifier.fillParentMaxHeight(0.85f) else Modifier.height(
                            96f.dp
                        )

                    Box(
                        Modifier
                            .clickable {

                                scope.launch {

                                    if (expandedIndex == index) {
                                        expandedIndex = -1
                                        state.animateScrollAndAlignItem(
                                            index * 2, 0.33f
                                        )
                                    } else {
                                        expandedIndex = index
                                        state.animateScrollAndAlignItem(
                                            index * 2, 0.04f
                                        )
                                    }
                                }


                            }
                            .padding(24f.dp)
                            .animateContentSize()
                            .background(Color.Cyan)
                            .fillMaxWidth()
                            .then(expandedModifier)) {
                        LazyHorizontalGrid(
                            rows = GridCells.Adaptive(120.dp),

                            horizontalArrangement = Arrangement.spacedBy(16f.dp),
                            verticalArrangement = Arrangement.spacedBy(16f.dp),
                        ) {
                            items(20, key = { it }) {
                                (it * 0xA1) % 0xAA
                                Box(
                                    Modifier
                                        .background(
                                            Color(
                                                (it * 0xA1) % 0xAA,
                                                (it * 0x61) % 0xCA,
                                                (it * 0x51) % 0x9A
                                            )
                                        )
                                        .size(100f.dp)
                                )
                            }
                        }
                    }
                }
                panelSeparator()
            }
        }
    }
}

suspend fun LazyListState.animateScrollAndAlignItem(index: Int, offsetRatio: Float) {
    val itemInfo = this.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    if (itemInfo != null) {
        val center = layoutInfo.viewportEndOffset * offsetRatio
        val childCenter = itemInfo.offset
        animateScrollBy((childCenter - center))
    } else {
        animateScrollToItem(index)
    }
}