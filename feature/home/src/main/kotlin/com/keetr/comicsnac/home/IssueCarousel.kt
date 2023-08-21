package com.keetr.comicsnac.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.keetr.comicsnac.home.fake.Issues
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
object CustomPageSize : PageSize {
    override fun Density.calculateMainAxisPageSize(availableSpace: Int, pageSpacing: Int): Int {
        return if (availableSpace > 2000) availableSpace / 5 else availableSpace / 3
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IssueCarousel(
    modifier: Modifier = Modifier, issues: List<Issue>, onIssueClick: (apiUrl: String) -> Unit
) {
    val pagerState =
        rememberPagerState(issues.size * (Int.MAX_VALUE / (2 * issues.size)) - 1) { Int.MAX_VALUE }
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        modifier = modifier.zIndex(1f),
        state = pagerState,
        pageSize = CustomPageSize,
        verticalAlignment = Alignment.CenterVertically,
        pageSpacing = 8f.dp
    ) { index ->
        val actualIndex = index % issues.size
        val pageOffset =
            ((pagerState.currentPage + 1 - index) + pagerState.currentPageOffsetFraction).absoluteValue
        with(issues[actualIndex]) {
            Column(
                modifier = Modifier.graphicsLayer {
                    val scaleFactor = lerp(
                        start = 1f, stop = 1.4f, fraction = (1f - pageOffset).coerceIn(-1.5f, 1.4f)
                    )
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }, horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    Modifier
                        .padding(top = 48f.dp)
                        .shadow(lerp(2f, 6f, 1f - pageOffset).dp)
                        .fillMaxWidth(0.85f)
                        .aspectRatio(11f / 17f)
                ) {
                    val overlay = MaterialTheme.colorScheme.surfaceVariant
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                            .crossfade(true).build(),
                        contentDescription = stringResource(
                            R.string.issue_image_desc,
                            issueNumber,
                            volumeName,
                            name
                        ),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(1f.dp)
                            .drawWithContent {
                                drawContent()
                                drawRect(
                                    overlay.copy(
                                        lerp(
                                            0.3f,
                                            0f,
                                            (1f - pageOffset).coerceIn(0.0f, 1f)
                                        )
                                    ), Offset.Zero, size
                                )
                            }
                            .clickable {
                                if (index - 1 == pagerState.currentPage) onIssueClick(apiDetailUrl)
                                else coroutineScope.launch { pagerState.animateScrollToPage(index - 1) }
                            }
                            .fillMaxSize()
                    )
                }

                Spacer(Modifier.height(4f.dp))
                Text(text = name,
                    textAlign = TextAlign.Center,
                    minLines = 2,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 48f.dp)
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = 0.8f
                            scaleY = scaleX
                        })
            }
        }
    }

}

//device = "spec:width=412px,height=360px,dpi=440,orientation=portrait"
@Preview()
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun Preview() {
    ComicSnacTheme {
        Surface(Modifier.fillMaxSize()) {
            IssueCarousel(issues = Issues) { }
        }
    }
}
