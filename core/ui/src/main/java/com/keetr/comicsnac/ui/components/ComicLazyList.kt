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

interface PanelLazyListScope {
    fun LazyListScope.panelSeparator()

    fun LazyListScope.panel(
        tag: String,
        content: @Composable BoxScope.() -> Unit
    )
}

private class PanelLazyListScopeImpl(colors: PanelColors) : PanelLazyListScope {

    private val colors: List<Color> = listOf(colors.surface1, colors.surface2, colors.surface3)
    private val strokeColor: Color = colors.strokeColor

    private var currColorIndex = 0
    private val nextColorIndex get() = (currColorIndex + 1) % 3

    private var seperatorCount = 0
    private var flipped = false

    override fun LazyListScope.panelSeparator() {
        item("separator#${++seperatorCount}") {
            ComicListSeperator(
                upperColor = colors[currColorIndex],
                lowerColor = colors[nextColorIndex],
                strokeColor = strokeColor,
                flipped = flipped
            )
            flipped = !flipped
            currColorIndex = nextColorIndex
        }
    }

    override fun LazyListScope.panel(
        tag: String,
        content: @Composable BoxScope.() -> Unit
    ) {
        item(tag) {
            Box(Modifier.background(colors[currColorIndex]), content = content)
        }
    }
}

fun LazyListScope.panelList(
    colors: PanelColors,
    content: PanelLazyListScope.() -> Unit,
) {
    val panelLazyListScope = PanelLazyListScopeImpl(colors)
    panelLazyListScope.content()
}


@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        val primary = MaterialTheme.colorScheme.primary
        val secondary = MaterialTheme.colorScheme.secondary
        val tertiary = MaterialTheme.colorScheme.tertiary
        val strokeColor = MaterialTheme.colorScheme.onSurface

        LazyColumn {
            panelList(
                PanelColors(strokeColor, primary, secondary, tertiary)
            ) {
                repeat(30) {

                    panel("Test $it") {
                        Box(
                            Modifier
                                .padding(24f.dp)
                                .background(Color.Cyan)
                                .fillMaxWidth()
                                .height(80f.dp)
                        )
                    }
                    panelSeparator()

                }
            }
        }
    }
}