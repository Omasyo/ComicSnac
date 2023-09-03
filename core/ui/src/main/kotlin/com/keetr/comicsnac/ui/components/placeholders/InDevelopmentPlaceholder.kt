package com.keetr.comicsnac.ui.components.placeholders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InDevelopmentPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier,
//            .fillMaxSize()
    ) {
        Text(
            "In Development",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}