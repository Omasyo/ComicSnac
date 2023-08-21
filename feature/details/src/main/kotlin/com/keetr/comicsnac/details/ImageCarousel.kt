package com.keetr.comicsnac.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest

internal data class Image(val url: String, val description: String?)


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LazyItemScope.ImageCarousel(
    modifier: Modifier = Modifier,
    images: List<Image>,
    imageExpanded: Boolean,
    onImageClicked: () -> Unit,
    lazyListState: LazyListState
) {
    val pagerState = rememberPagerState { images.size }
    val imageModifier = remember(imageExpanded) {
        if (imageExpanded) Modifier.fillParentMaxSize() else Modifier
            .fillMaxSize(0.7f)
            .aspectRatio(11f / 17f)
    }
    val pagerModifier = remember(imageExpanded) {
        if (imageExpanded) Modifier.fillParentMaxSize() else Modifier
            .fillMaxWidth()
            .fillParentMaxHeight(0.75f)
    }

    HorizontalPager(
        modifier = modifier
            .animateContentSize()
            .then(pagerModifier),
        state = pagerState,
        pageSize = PageSize.Fill,
        verticalAlignment = Alignment.CenterVertically
    ) { index ->
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .graphicsLayer {
                        if (lazyListState.firstVisibleItemIndex == 0) {
                            scaleX = lerp(
                                0.2f,
                                1.0f,
                                1f - (lazyListState.firstVisibleItemScrollOffset.toFloat() / lazyListState.layoutInfo.viewportEndOffset)
                            )
                            scaleY = scaleX
                        }
                    }
                    .background(Color.White.copy(0.2f))
                    .then(imageModifier)
                    .clickable { onImageClicked() }
                    .align(Alignment.Center)
                    .shadow(
                        elevation = 16f.dp,
                        ambientColor = MaterialTheme.colorScheme.onSurface,
                        spotColor = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                Box(
                    Modifier
                        .background(Color.Gray)
                        .fillMaxSize()
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[index].url).crossfade(true).build(),
                    contentDescription = images[index].description, //Add proper string resource
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}