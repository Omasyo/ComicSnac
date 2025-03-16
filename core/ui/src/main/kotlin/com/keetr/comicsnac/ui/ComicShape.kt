package com.keetr.comicsnac.ui

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import kotlin.random.Random

const val Variation = 0.1

@Composable
fun rememberRandomComicShape(): Shape = rememberSaveable(
    saver = ComicShape.Saver
) { createRandomComicShape() }

private class ComicShape private constructor(
    val topStart: Offset,
    val topEnd: Offset,
    val bottomStart: Offset,
    val bottomEnd: Offset,
    genericShape: GenericShape
) : Shape by genericShape {
    constructor(
        topStart: Offset,
        topEnd: Offset,
        bottomStart: Offset,
        bottomEnd: Offset,
    ) : this(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = bottomStart,
        bottomEnd = bottomEnd,
        genericShape = GenericShape { size, _ ->
            with(size) {
                moveTo(topStart.x * width, topStart.y * height)
                lineTo(topEnd.x * width, topEnd.y * height)
                lineTo(bottomEnd.x * width, bottomEnd.y * height)
                lineTo(bottomStart.x * width, bottomStart.y * height)
                close()
            }
        })

    companion object {
        val Saver: Saver<ComicShape, *> = listSaver(
            save = {
                listOf(
                    it.topStart.x,
                    it.topStart.y,
                    it.topEnd.x,
                    it.topEnd.y,
                    it.bottomStart.x,
                    it.bottomStart.y,
                    it.bottomEnd.x,
                    it.bottomEnd.y,
                )
            },
            restore = {
                ComicShape(
                    topStart = Offset(it[0], it[1]),
                    topEnd = Offset(it[2], it[3]),
                    bottomStart = Offset(it[4], it[5]),
                    bottomEnd = Offset(it[6], it[7]),
                )
            }
        )
    }
}

private fun createRandomComicShape(): ComicShape {
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
        return ComicShape(topStart, topEnd, bottomStart, bottomEnd)
    }
}