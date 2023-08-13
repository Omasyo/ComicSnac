package com.keetr.comicsnac.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun ComicListSeperator(
    modifier: Modifier = Modifier,
    upperColor: Color,
    lowerColor: Color,
    strokeColor: Color,
    flipped: Boolean,
    aspectRatio: Float = Random.nextDouble(12.0, 32.0).toFloat()
) {
    Canvas(modifier = modifier
        .scale(if(flipped) -1f else 1f, 1f)
        .aspectRatio(aspectRatio)
//        .fillMaxWidth()
    ) {
        with(size) {
            val upperTrianglePath = Path().apply {
                moveTo(0f, 0f)
                lineTo(width, 0f)
                lineTo(width, height)
                close()
            }
            val lowerTrianglePath = Path().apply {
                moveTo(0f, 0f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(upperTrianglePath, upperColor)
            drawPath(lowerTrianglePath, lowerColor)

            drawLine(strokeColor, Offset.Zero, Offset(size.width, size.height), 4f.dp.toPx())
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicListSeperator(
        upperColor = Color.Blue,
        lowerColor = Color.Red,
        strokeColor = Color.Yellow,
        flipped = false,
        aspectRatio = Random.nextDouble(8.0, 12.0).toFloat()
    )
}