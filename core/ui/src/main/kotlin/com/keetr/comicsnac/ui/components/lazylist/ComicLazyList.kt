package com.keetr.comicsnac.ui.components.lazylist

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class PanelColors(
    val strokeColor: Color,
    val surface1: Color,
    val surface2: Color,
    val surface3: Color,
)

interface PanelLazyListScope {
    fun panel(
        content: @Composable LazyItemScope.(backgroundColor: Color) -> Unit,
    )

    fun panelSeparator(
        content: (@Composable LazyItemScope.(
            upperColor: Color,
            lowerColor: Color, strokeColor: Color, flipped: Boolean
        ) -> Unit)? = null
    )
}


@Composable
fun PanelList(
    modifier: Modifier = Modifier,
    colors: PanelColors = with(MaterialTheme.colorScheme) {
        PanelColors(onSurface, primary, secondary, tertiary)
    },
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: PanelLazyListScope.() -> Unit,
) {
    val panelLazyListScope =
        DefaultPanelListScope(colors)
    panelLazyListScope.content()

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        flingBehavior = flingBehavior,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        reverseLayout = reverseLayout,
        userScrollEnabled = userScrollEnabled,
    ) {
        panelLazyListScope.render(this)
    }
}

private sealed interface PanelListItem

private data class Panel(
    val content: @Composable LazyItemScope.(backgroundColor: Color) -> Unit,
    val colorId: Int
) : PanelListItem

private data class PanelListSeparator(
    val content: @Composable LazyItemScope.(
        upperColor: Color,
        lowerColor: Color,
        strokeColor: Color,
        flipped: Boolean
    ) -> Unit,
    val colorId: Int
) : PanelListItem


private class DefaultPanelListScope(colors: PanelColors) : PanelLazyListScope {

    private val colors: List<Color> = listOf(colors.surface1, colors.surface2, colors.surface3)
    private val strokeColor: Color = colors.strokeColor

    private val panels: MutableList<PanelListItem> = mutableListOf()

    private var colorId = 0

    override fun panel(content: @Composable (LazyItemScope.(backgroundColor: Color) -> Unit)) {
        panels.add(Panel(content, colorId))
    }

    override fun panelSeparator(content: @Composable (LazyItemScope.(upperColor: Color, lowerColor: Color, strokeColor: Color, flipped: Boolean) -> Unit)?) {
        if (content == null) {
            val separator: @Composable LazyItemScope.(upperColor: Color, lowerColor: Color, strokeColor: Color, flipped: Boolean) -> Unit =
                { upperColor, lowerColor, strokeColor, flipped ->
                    ComicListSeparator(
                        upperColor = upperColor,
                        lowerColor = lowerColor,
                        strokeColor = strokeColor,
                        flipped = flipped
                    )
                }
            panels.add(PanelListSeparator(separator, colorId))
        } else {
            panels.add(PanelListSeparator(content, colorId))
        }
        ++colorId
    }

    fun render(scope: LazyListScope) {

        if (panels.isEmpty()) return
        for (index in 0 until panels.size) {
            when (val panel = panels[index]) {
                is Panel -> {
                    val (content, colorId) = panel
                    val colorIndex = colorId % 3

                    scope.item {
                        Box(Modifier.background(colors[colorIndex])) {
                            content(colors[colorIndex])
                        }
                    }
                }

                is PanelListSeparator -> {

                    val (content, colorId) = panel
                    val colorIndex = colorId % 3
                    val nextColorIndex = (colorIndex + 1) % 3

                    scope.item {
                        content(
                            colors[colorIndex],
                            colors[nextColorIndex],
                            strokeColor,
                            flipped = colorId % 2 == 0
                        )
                    }
                }
            }

        }
    }

}