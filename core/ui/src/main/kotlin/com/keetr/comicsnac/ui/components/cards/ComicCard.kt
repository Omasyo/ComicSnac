package com.keetr.comicsnac.ui.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.keetr.comicsnac.ui.createRandomComicShape
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = remember {
        createRandomComicShape()
    }
    Box(
        modifier
            .clip(shape)
            .clickable(onClick != null) { onClick?.invoke() }
            .border(4f.dp, MaterialTheme.colorScheme.outline, shape)
            .background(backgroundColor),
        content = content
    )
}

@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String?,
    onClick: () -> Unit
) {
    ComicCard(modifier, onClick = onClick) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl).crossfade(true).build(),
            contentDescription = contentDescription, //Add proper string resource
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Column(modifier) {
        ComicCard(
            Modifier.weight(1f),
            imageUrl = imageUrl,
            contentDescription = contentDescription,
            onClick = onClick
        )
        Text(
            name,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8f.dp),
            textAlign = TextAlign.Center,
            minLines = 2,
            maxLines = 2
        )
    }
}

@Preview(
    heightDp = 216, widthDp = 136,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun Preview() {

    ComicSnacTheme {
//        ComicCard {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .background(Color.Gray)
//            )
//        }
        ComicCard(name = "Hisdf" ,imageUrl = "", contentDescription = "") {}
    }
}