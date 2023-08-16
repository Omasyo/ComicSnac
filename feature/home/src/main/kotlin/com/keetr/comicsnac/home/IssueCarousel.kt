package com.keetr.comicsnac.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.keetr.comicsnac.model.issue.Issue
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IssueCarousel(
    modifier: Modifier = Modifier,
    issues: List<Issue>
) {
    val pagerState = rememberPagerState(issues.lastIndex) { Int.MAX_VALUE }
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        pageSize = PageSize.Fixed(128f.dp),
        verticalAlignment = Alignment.CenterVertically,
        pageSpacing = 8f.dp
    ) { index ->
        val actualIndex = index % issues.size
        with(issues[actualIndex]) {
            Column(
                modifier = Modifier.graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage + 1 - index) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    val scaleFactor = lerp(
                        start = 1f,
                        stop = 1.4f,
                        fraction = 1f - pageOffset
                    )
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                },
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                ElevatedCard(
                    modifier = Modifier
                        .aspectRatio(184f / 288f)
                        .fillMaxWidth(0.25f),
                    onClick = { /*TODO*/ },
                    shape = RectangleShape
                ) {

                }
                Spacer(Modifier.height(8f.dp))
                Text(name, textAlign = TextAlign.Center)
            }
        }
    }

}

//device = "spec:width=412px,height=360px,dpi=440,orientation=portrait"
@Preview()
@Composable
private fun Preview() {
    IssueCarousel(issues = Issues)
}

val Issues = List(5) {
    Issue(
        apiDetailUrl = "https://search.yahoo.com/search?p=qui",
        deck = "vix",
        id = 5697,
        imageUrl = "https://comicvine.gamespot.com/a/uploads/scale_small/6/67663/2710974-698.jpg",
        name = "Angelo Espinoza $it",
        siteDetailUrl = "https://search.yahoo.com/search?p=magnis"

    )
}