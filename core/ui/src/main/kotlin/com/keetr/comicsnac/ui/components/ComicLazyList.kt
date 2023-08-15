package com.keetr.comicsnac.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlin.random.Random

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
        content: @Composable LazyItemScope.() -> Unit
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
        content: @Composable LazyItemScope.() -> Unit
    ) {
        item(tag) {
            Box(Modifier.background(colors[currColorIndex])) {
                this@item.content()
            }
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

        LazyColumn(Modifier.fillMaxSize()) {

            panelList(
                PanelColors(strokeColor, primary, secondary, tertiary)
            ) {
                repeat(30) {

                    panel("Test $it") {
                        var expanded by remember {
                            mutableStateOf(false)
                        }
                        val expandedModifier =
                            if (expanded) Modifier.fillParentMaxSize(0.8f) else Modifier
                                .height(Random.nextInt(64, 160).dp)

                        Box(
                            Modifier
                                .clickable { expanded = !expanded }
                                .padding(24f.dp)
                                .animateContentSize()
                                .background(Color.Cyan)
                                .fillMaxWidth()
                                .then(expandedModifier)
                        ) {
                            Text("Box $it")
                        }
                    }
                    panelSeparator()

                }
            }
        }
    }
}