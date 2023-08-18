package com.keetr.comicsnac.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.components.lazylist.panelList
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    imageUrl: List<String>,
    content: PanelLazyListScope.(LazyListScope) -> Unit
) {
    Scaffold { paddingValues ->
        val panelColors = with(MaterialTheme.colorScheme) {
            PanelColors(
                strokeColor = outline, surface1 = primary, surface2 = tertiary, surface3 = secondary
            )
        }
        LazyColumn(contentPadding = paddingValues) {
            panelList(panelColors) {
                content(this@LazyColumn)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        DetailsScreen(imageUrl = listOf("")) {
            with(it) {

            }
        }
    }
}