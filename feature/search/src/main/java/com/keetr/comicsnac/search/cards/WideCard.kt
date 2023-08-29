package com.keetr.comicsnac.search.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun WideCard(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    onClick: () -> Unit,
    imageUrl: String,
    type: String,
    background: Color = MaterialTheme.colorScheme.secondary,
    imageDescription: String
) = WideCard(
    modifier = modifier.fillMaxWidth().height(200f.dp),
    name = name,
    onClick = onClick,
    imageUrl = imageUrl,
    type = type,
    background = background,
    imageDescription = imageDescription
) {
    Text(description, overflow = TextOverflow.Ellipsis)
}


@Composable
fun WideCard(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit,
    imageUrl: String,
    imageDescription: String,
    type: String,
    background: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier
            .clickable { onClick() }
            .border(4f.dp, MaterialTheme.colorScheme.outline, RectangleShape)
            .background(background)
            .padding(16f.dp)
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl).crossfade(true).build(),
                contentDescription = imageDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(11f / 17f)
            )
            Spacer(Modifier.width(8f.dp))
            Column(Modifier.fillMaxWidth()) {
                Text(
                    type,
                    Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(Modifier.height(16f.dp))
                Text(name, style = MaterialTheme.typography.titleLarge)
                content()

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        Box(Modifier.fillMaxSize()) {
            WideCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                name = "Ernesto Crawford",
                description = "contentiones asdfsadf asdf asdfasd fasdf asdfas df asdf as df a sd fa sd fa sdg asdgsdfgdsg sdfg sdfgsdfg sdfgsdfgsd gfsdfg dsfgsdfgasdfgasdf sadfasdfasf ",
                imageUrl = "http://www.bing.com/search?q=aenean",
                imageDescription = "conclusionemque",
                type = "Character",
                background = MaterialTheme.colorScheme.secondary,
                onClick = {},
            )
        }
    }
}