package com.keetr.comicsnac.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.placeholders.InDevelopmentPlaceholder
import com.keetr.comicsnac.ui.placeholders.LoadingPlaceholder

@Composable
internal fun <T> CategoryCarousel(
    modifier: Modifier = Modifier,
    name: String,
    uiState: HomeCategoryUiState<T>,
    key: (item: T) -> Any,
    builder: @Composable (item: T) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
    ) {
        Text(
            name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16f.dp, top = 8f.dp)
        )

        AnimatedContent(
            targetState = uiState,
            modifier = Modifier.animateContentSize(),
            label = "Category Carousel"
        ) { uiState ->
            when (uiState) {
                is Error -> ErrorPlaceholder(Modifier.height(300f.dp))
                InDevelopment -> InDevelopmentPlaceholder(Modifier.height(300f.dp))
                Loading -> LoadingPlaceholder(Modifier.height(300f.dp))

                is Success -> {
                    LazyRow(
                        contentPadding = PaddingValues(16f.dp, 8f.dp),
                        horizontalArrangement = Arrangement.spacedBy(4f.dp)
                    ) {
                        items(uiState.contents, key = key) {
                            builder(it)
                        }
                    }
                }
            }
        }
    }
}