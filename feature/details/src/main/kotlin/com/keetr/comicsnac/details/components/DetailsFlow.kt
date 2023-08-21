package com.keetr.comicsnac.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T> LazyItemScope.DetailsFlow(
    modifier: Modifier = Modifier,
    name: String,
    items: List<T>,
    builder: @Composable (T) -> Unit
) {
    Column(modifier) {
        Text(
            name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16f.dp, top = 8f.dp)
        )
        FlowRow(
            Modifier
                .fillMaxWidth()
                .padding(16f.dp),
            verticalArrangement = Arrangement.spacedBy(32f.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (item in items) {
                builder(item)
            }
        }
    }
}