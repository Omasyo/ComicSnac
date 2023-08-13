package com.keetr.comicsnac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

data class PanelColors(
    val strokeColor: Color,
    val surface1: Color,
    val surface2: Color,
    val surface3: Color,
)

class ListScope(colors: PanelColors) {

    private val colors: List<Color> = listOf(colors.surface1, colors.surface2, colors.surface3)
    private val strokeColor: Color = colors.strokeColor

    private var currColorIndex = 0
    private val nextColorIndex get() = (currColorIndex + 1) % 3

    private var seperatorCount = 0

    fun LazyListScope.panelSeperator(
        modifier: Modifier = Modifier,
    ) {
        item("separator#${++seperatorCount}") {
            ComicListSeperator(
                upperColor = colors[currColorIndex],
                lowerColor = colors[nextColorIndex],
                strokeColor = strokeColor,
                flipped = currColorIndex % 2 != 0
            )
            currColorIndex = nextColorIndex
        }
    }

    fun LazyListScope.panel(
        tag: String,
        backgroundColor: Color,
        content: @Composable BoxScope.() -> Unit
    ) {
        item(tag) {
            Box(Modifier.background(colors[currColorIndex]), content = content)
        }
    }
}

fun LazyListScope.panelList(
    colors: PanelColors,
    content: ListScope.() -> Unit,
) {
    val listScope = ListScope(colors)
    listScope.content()
}


@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        val primary = MaterialTheme.colorScheme.primary
        val secondary = MaterialTheme.colorScheme.secondary
        val tetiary = MaterialTheme.colorScheme.tertiary
        val strokeColor = MaterialTheme.colorScheme.onSurface

        LazyColumn {
            panelList(
                PanelColors(strokeColor, primary, secondary, tetiary)
            ) {

                panel("Test 1", primary) {
                    Box(
                        Modifier
                            .padding(24f.dp)
                            .background(Color.Cyan)
                            .fillMaxWidth()
                            .height(80f.dp)
                    )
                }

                panelSeperator()

                panel("Test 2", secondary) {
                    Box(
                        Modifier
                            .padding(24f.dp)
                            .background(Color.Magenta)
                            .fillMaxWidth()
                            .height(80f.dp)
                    )
                }

                panelSeperator()

                panel("Test 3", tetiary) {
                    Box(
                        Modifier
                            .padding(24f.dp)
                            .background(Color.DarkGray)
                            .fillMaxWidth()
                            .height(80f.dp)
                    )
                }

                panelSeperator()

                panel("Test 4", primary) {
                    Box(
                        Modifier
                            .padding(24f.dp)
                            .background(Color.DarkGray)
                            .fillMaxWidth()
                            .height(80f.dp)
                    )
                }
            }
        }
    }
}