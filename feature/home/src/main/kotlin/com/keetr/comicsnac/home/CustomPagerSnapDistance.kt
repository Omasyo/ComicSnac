package com.keetr.comicsnac.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
object CustomPagerSnapDistance : PagerSnapDistance {
    override fun calculateTargetPage(
        startPage: Int,
        suggestedTargetPage: Int,
        velocity: Float,
        pageSize: Int,
        pageSpacing: Int
    ): Int {

        Log.d(
            "CustomPagerSnap",
            "calculateTargetPage: $startPage, $suggestedTargetPage, $velocity, $pageSize, $pageSpacing"
        )
        val offset = velocity.roundToInt() / 1200
        val mySuggestedTarget = startPage + offset
        return mySuggestedTarget
    }
}