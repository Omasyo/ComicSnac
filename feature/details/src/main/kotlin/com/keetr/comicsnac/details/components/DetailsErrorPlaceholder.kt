package com.keetr.comicsnac.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.keetr.comicsnac.ui.components.lazylist.PanelList
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlinx.coroutines.delay
import com.keetr.comicsnac.ui.R.string as CommonString


@Composable
internal fun DetailsErrorPlaceholder(
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    onBackPressed: () -> Unit,
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
                colors = panelColors,
                userScrollEnabled = false
            ) {
                panel(true) {
                    Box(
                        Modifier
                            .fillParentMaxHeight(0.75f)
                            .fillMaxWidth()
                    ) {
                        Box(
                            Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(0.7f)
                                .aspectRatio(11f / 17f)
                                .padding(32f.dp)
                        ) {
                            ErrorPlaceholder(modifier = Modifier.fillMaxSize(),onRetry = onRetry)
                        }
                    }

                    Box(Modifier
                        .padding(16f.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            onBackPressed()
                        }
                        .size(56f.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            AppIcons.ArrowBack,
                            contentDescription = stringResource(CommonString.back_button_desc)
                        )
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
                panel {
                    var dotCount by remember {
                        mutableIntStateOf(1)
                    }
                    val next = dotCount % 12

                    LaunchedEffect(key1 = Unit) {
                        while (true) {
                            dotCount = dotCount.plus(1) % 800
                            delay(450)
                        }
                    }

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16f.dp, vertical = 8f.dp),
                        verticalArrangement = Arrangement.spacedBy(4f.dp),
                    ) {
                        Text(
                            PlaceholderName,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            "*".repeat(400),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        DetailsErrorPlaceholder(onRetry = {}) {

        }
    }
}
