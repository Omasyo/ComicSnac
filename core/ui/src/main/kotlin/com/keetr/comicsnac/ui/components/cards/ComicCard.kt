package com.keetr.comicsnac.ui.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.createRandomComicShape
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = remember {
        createRandomComicShape()
    }
    Box(
        modifier
            .border(4f.dp, MaterialTheme.colorScheme.outline, shape)
            .clip(shape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        content()
    }
}

@Preview(heightDp = 216, widthDp = 136,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun Preview() {

    ComicSnacTheme {
        ComicCard {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }
    }
}