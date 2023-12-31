package com.keetr.comicsnac.ui.components.placeholders

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun LoadingPlaceholder(
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {

    var dotCount by remember {
        mutableIntStateOf(1)
    }
//    val next = dotCount.plus(1) % 3

    LaunchedEffect(key1 = Unit) {
        while (true) {
            dotCount = dotCount.plus(1) % 4
            delay(450)
        }
    }

    Box(
        modifier
//            .fillMaxSize()
    ) {
        Text(
            "Loading${".".repeat(dotCount)}",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center),
            color = color
        )
    }
}