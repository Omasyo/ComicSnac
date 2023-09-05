package com.keetr.comicsnac.details.components.panels

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.details.R
import com.keetr.comicsnac.ui.components.lazylist.ComicListSeparator
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.components.webview.ComicWebView


internal fun PanelLazyListScope.webViewPanel(
    description: AnnotatedString,
    expandedProvider: (Int) -> Boolean,
    onToggleExpand: (Int) -> Unit,
    onItemClicked: (String) -> Unit
) {

    specialPanel { index ->

        val expanded = expandedProvider(index)

        val heightModifier = remember(expanded) {
            if (expanded) Modifier.fillParentMaxHeight() else Modifier.height(400.dp)
        }

        BackHandler(expanded) {
            onToggleExpand(index)
        }

        Box(
            Modifier
                .testTag("web_view")
                .animateContentSize()
                .fillMaxWidth()
                .then(heightModifier),
        ) {
            ComicWebView(
                annotatedString = description,
                contentPadding = PaddingValues(
                    vertical = 40f.dp,
                    horizontal = 16f.dp
                ),
                scrollable = expanded,
                onLinkClick = onItemClicked
            )

            Text(
                stringResource(if (expanded) R.string.collapse else R.string.expand),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .testTag("expand_button")
                    .align(Alignment.TopEnd)
                    .padding(top = 8f.dp)
                    .clickable {
                        onToggleExpand(index)
                    }
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16f.dp, vertical = 4f.dp))

        }
    }
}

private fun PanelLazyListScope.specialPanel(
    content: @Composable LazyItemScope.(Int) -> Unit,
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
            content(it)
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