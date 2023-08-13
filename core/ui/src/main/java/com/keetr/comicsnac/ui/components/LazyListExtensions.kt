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

//class ListScope {
//
//}
//
//fun LazyListScope.panelList(content: ())

fun LazyListScope.panelSeperator(
    modifier: Modifier = Modifier,
    upperColor: Color,
    lowerColor: Color,
    strokeColor: Color,
    flipped: Boolean,
) {
    item {
        ComicListSeperator(
            upperColor = upperColor,
            lowerColor = lowerColor,
            strokeColor = strokeColor,
            flipped = flipped
        )
    }
}

fun LazyListScope.panel(
    tag: String,
    backgroundColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    item(tag) {
        Box(Modifier.background(backgroundColor), content = content)
    }
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

            panel("Test 1", primary) {
                Box(
                    Modifier
                        .padding(24f.dp)
                        .background(Color.Cyan)
                        .fillMaxWidth()
                        .height(80f.dp)
                )
            }

            panelSeperator(
                upperColor = primary,
                lowerColor = secondary,
                strokeColor = strokeColor,
                flipped = false
            )

            panel("Test 2", secondary) {
                Box(
                    Modifier
                        .padding(24f.dp)
                        .background(Color.Magenta)
                        .fillMaxWidth()
                        .height(80f.dp)
                )
            }

            panelSeperator(
                upperColor = secondary,
                lowerColor = tetiary,
                strokeColor = strokeColor,
                flipped = true
            )

            panel("Test 3", tetiary) {
                Box(
                    Modifier
                        .padding(24f.dp)
                        .background(Color.DarkGray)
                        .fillMaxWidth()
                        .height(80f.dp)
                )
            }

            panelSeperator(
                upperColor = tetiary,
                lowerColor = primary,
                strokeColor = strokeColor,
                flipped = false
            )

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