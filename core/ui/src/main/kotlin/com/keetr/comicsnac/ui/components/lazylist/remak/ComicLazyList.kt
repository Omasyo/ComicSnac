//package com.keetr.comicsnac.ui.components.lazylist
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.animateScrollBy
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyItemScope
//import androidx.compose.foundation.lazy.LazyListScope
//import androidx.compose.foundation.lazy.LazyListState
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.keetr.comicsnac.ui.components.lazylist.remak.PanelColors
//import com.keetr.comicsnac.ui.components.lazylist.remak.PanelLazyListScope
//import com.keetr.comicsnac.ui.theme.ComicSnacTheme
//import kotlinx.coroutines.launch
//
//data class PanelColors(
//    val strokeColor: Color,
//    val surface1: Color,
//    val surface2: Color,
//    val surface3: Color,
//)
//
//interface PanelLazyListScope {
//
//    fun LazyListScope.panel(
//        content: @Composable LazyItemScope.() -> Unit
//    )
//
//    fun render(scope: LazyListScope)
//}
//
//private class PanelLazyListScopeImpl(colors: PanelColors) : PanelLazyListScope {
//
//    private val colors: List<Color> = listOf(colors.surface1, colors.surface2, colors.surface3)
//    private val strokeColor: Color = colors.strokeColor
//
//
//    private val panels: MutableList<@Composable LazyItemScope.() -> Unit> = mutableListOf()
//
//    override fun LazyListScope.panel(
//        content: @Composable LazyItemScope.() -> Unit
//    ) {
//        panels.add(content)
//    }
//
//    override fun render(scope: LazyListScope) {
//        if (panels.isEmpty()) return
//        for (index in 0 until panels.lastIndex) {
//            val colorIndex = index % 3
//            val nextColorIndex = (colorIndex + 1) % 3
//
//            scope.item("panel$index") {
//                Column {
//                    Box(Modifier.background(colors[colorIndex])) {
//                        panels[index]()
//                    }
//                }
//                ComicListSeparator(
//                    upperColor = colors[colorIndex],
//                    lowerColor = colors[nextColorIndex],
//                    strokeColor = strokeColor,
//                    flipped = index % 2 == 0
//                )
//            }
//        }
//        val lastIndex = panels.lastIndex
//        scope.item("panel$${panels.lastIndex}") {
//
//            Box(Modifier.background(colors[lastIndex % 3])) {
//                panels.last()()
//            }
//        }
//    }
//}
//
//fun LazyListScope.panelList(
//    colors: PanelColors,
//    content: PanelLazyListScope.() -> Unit,
//) {
//    val panelLazyListScope = PanelLazyListScopeImpl(colors)
//    panelLazyListScope.content()
//
//    panelLazyListScope.render(this)
//}
//
//
//@Preview
//@Composable
//private fun Preview() {
//    ComicSnacTheme {
//        val primary = MaterialTheme.colorScheme.primary
//        val secondary = MaterialTheme.colorScheme.secondary
//        val tertiary = MaterialTheme.colorScheme.tertiary
//        val strokeColor = MaterialTheme.colorScheme.onSurface
//
//        val state = rememberLazyListState()
//        val scope = rememberCoroutineScope()
//        var expandedIndex by remember {
//            mutableIntStateOf(-1)
//        }
//
//        LazyColumn(
//            Modifier.fillMaxSize(), state = state, userScrollEnabled = expandedIndex < 0
//        ) {
//
//            panelList(
//                PanelColors(strokeColor, primary, secondary, tertiary)
//            ) {
//                repeat(30) { index ->
//
//                    panel {
//                        val expandedModifier =
//                            if (index == expandedIndex) Modifier.fillParentMaxHeight(0.85f) else Modifier.height(
//                                96f.dp
//                            )
//
//                        Box(
//                            Modifier
//                                .clickable {
//
//                                    scope.launch {
//
//                                        if (expandedIndex == index) {
//                                            expandedIndex = -1
//                                            state.animateScrollAndAlignItem(
//                                                index, 0.33f
//                                            )
//                                        } else {
//                                            expandedIndex = index
//                                            state.animateScrollAndAlignItem(
//                                                index, 0.04f
//                                            )
//                                        }
//                                    }
//
//
//                                }
//                                .padding(24f.dp)
//                                .animateContentSize()
//                                .background(Color.Cyan)
//                                .fillMaxWidth()
//                                .then(expandedModifier)) {
//                            LazyHorizontalGrid(
//                                rows = GridCells.Adaptive(120.dp),
//
//                                horizontalArrangement = Arrangement.spacedBy(16f.dp),
//                                verticalArrangement = Arrangement.spacedBy(16f.dp),
//                            ) {
//                                items(20, key = { it }) {
//                                    (it * 0xA1) % 0xAA
//                                    Box(
//                                        Modifier
//                                            .background(
//                                                Color(
//                                                    (it * 0xA1) % 0xAA,
//                                                    (it * 0x61) % 0xCA,
//                                                    (it * 0x51) % 0x9A
//                                                )
//                                            )
//                                            .size(100f.dp)
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//suspend fun LazyListState.animateScrollAndAlignItem(index: Int, offsetRatio: Float) {
//    val itemInfo = this.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
//    if (itemInfo != null) {
//        val center = layoutInfo.viewportEndOffset * offsetRatio
//        val childCenter = itemInfo.offset
//        animateScrollBy((childCenter - center))
//    } else {
//        animateScrollToItem(index)
//    }
//}