package com.keetr.comicsnac.ui

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import kotlin.random.Random

const val Variation = 0.1

fun createRandomComicShape(): Shape {
    with(Random) {
        val topStart = Offset(nextDouble(Variation).toFloat(), nextDouble(Variation).toFloat())
        val topEnd =
            Offset(nextDouble(1.0 - Variation, 1.0).toFloat(), nextDouble(Variation).toFloat())
        val bottomStart =
            Offset(nextDouble(Variation).toFloat(), nextDouble(1.0 - Variation, 1.0).toFloat())
        val bottomEnd = Offset(
            nextDouble(1.0 - Variation, 1.0).toFloat(),
            nextDouble(1.0 - Variation, 1.0).toFloat()
        )
        return createComicShape(topStart, topEnd, bottomStart, bottomEnd)
    }
}

private fun createComicShape(
    topStart: Offset,
    topEnd: Offset,
    bottomStart: Offset,
    bottomEnd: Offset
) = GenericShape { size, _ ->
    with(size) {
        moveTo(topStart.x * width, topStart.y * height)
        lineTo(topEnd.x * width, topEnd.y * height)
        lineTo(bottomEnd.x * width, bottomEnd.y * height)
        lineTo(bottomStart.x * width, bottomStart.y * height)
        close()
    }
}