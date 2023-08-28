package com.keetr.comicsnac.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.components.lazylist.ComicListSeparator
import com.keetr.comicsnac.ui.components.lazylist.PanelColors
import com.keetr.comicsnac.ui.components.lazylist.PanelLazyListScope
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.R.string as CommonString


@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    images: List<Image>,
    onBackPressed: () -> Unit,
    userScrollEnabled: Boolean = true,
    imageExpanded: Boolean,
    onImageClicked: () -> Unit,
    onImageClose: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    content: PanelLazyListScope.() -> Unit
) {
    Scaffold(
        modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { paddingValues ->
        Box {
            val panelColors = with(MaterialTheme.colorScheme) {
                PanelColors(
                    strokeColor = outline,
                    surface1 = secondary,
                    surface2 = primary,
                    surface3 = tertiary
                )
            }
            PanelList(
                contentPadding = paddingValues,
                state = lazyListState,
                colors = panelColors,
                userScrollEnabled = userScrollEnabled
            ) {
                panel(true) {
                    ImageCarousel(
                        images = images,
                        lazyListState = lazyListState,
                        imageExpanded = imageExpanded,
                        onImageClicked = onImageClicked,
                    )

                    Box(Modifier
                        .padding(16f.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            if (imageExpanded) onImageClose() else onBackPressed()
                        }
                        .size(56f.dp), contentAlignment = Alignment.Center) {
                        AnimatedContent(
                            targetState = imageExpanded, transitionSpec = {
                                scaleIn() togetherWith scaleOut()
                            }, label = "carousel_image"
                        ) { imageExpanded ->
                            if (imageExpanded) {
                                Icon(
                                    AppIcons.Close,
                                    contentDescription = stringResource(CommonString.close_button_desc)
                                )
                            } else {
                                Icon(
                                    AppIcons.ArrowBack,
                                    contentDescription = stringResource(CommonString.back_button_desc)
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = imageExpanded,
                        enter = scaleIn(),
                        exit = scaleOut(),
                        modifier = Modifier
                            .padding(16f.dp)
                            .align(Alignment.TopEnd)
                            .size(56f.dp)
                    ) {
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .clickable { }
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                AppIcons.Share,
                                contentDescription = stringResource(CommonString.share_button_desc)
                            )
                        }
                    }
                }
                panelSeparator { _, lowerColor, strokeColor, flipped ->
                    ComicListSeparator(
                        upperColor = Color.Transparent,
                        lowerColor = lowerColor,
                        strokeColor = strokeColor,
                        flipped = flipped
                    )
                }
                content()
            }
        }
    }
}

@Composable
internal fun Info(
    name: String, content: String, onItemClicked: (() -> Unit)? = null
) {
    Row(Modifier.fillMaxWidth()) {
        Text("$name: ", style = MaterialTheme.typography.titleLarge)
        Text(content,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable(onItemClicked != null) { onItemClicked?.invoke() })
    }
}


@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        var expanded by remember {
            mutableStateOf(false)
        }
        DetailsScreen(
            images = List(5) { Image(it.toString(), null) },
            imageExpanded = expanded,
            onImageClicked = { expanded = true },
            onImageClose = {expanded = false},
            onBackPressed = { expanded = false }
        ) {
            repeat(4) {
                panel {
                    Box(
                        Modifier
                            .padding(24f.dp)
                            .background(Color.Red)
                            .fillMaxWidth()
                            .height(120f.dp)
                    )
                }
                panelSeparator()
            }

            panel {
                Box(
                    Modifier
                        .padding(24f.dp)
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(120f.dp)
                )
            }
        }
    }
}

