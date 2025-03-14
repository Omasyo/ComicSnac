package com.keetr.comicsnac.ui.components.webview

import android.graphics.Typeface
import android.net.Uri
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.ArrayDeque

const val TAG = "SComicWebView"

@Deprecated("", ReplaceWith("ComicWebView(modifier, contentPadding, annotatedString, scrollable)"))
@Composable
fun ComicWebView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0f.dp),
    annotatedString: AnnotatedString,
    scrollable: Boolean = true,
    onLinkClick: (String) -> Unit,
) = ComicWebView(
    modifier, contentPadding, ComicWebViewContent(annotatedString, emptyList()), scrollable
)

@Composable
fun ComicWebView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0f.dp),
    content: ComicWebViewContent,
    scrollable: Boolean = true,
) {
    val strings: List<WebviewContentItem> = remember {
        buildList {
            with(content) {
                val deque = ArrayDeque(content.images)
                var nextImage = deque.poll()

                var start = 0
                var end: Int

                do {
                    if (nextImage != null && nextImage.startIndex < start + 400) {
                        val image = nextImage
                        nextImage = deque.poll()

                        end = image.startIndex
                        add(
                            WebviewContentItem.Text(
                                text.subSequence(
                                    TextRange(
                                        start, end
                                    )
                                )
                            )
                        )
                        add(WebviewContentItem.Image(image.url))
                        start = image.endIndex

                    } else {
                        end = text.indexOf('\n', start + 400)
                        if (end < 0) {
                            add(
                                WebviewContentItem.Text(
                                    text.subSequence(
                                        TextRange(
                                            start, text.length
                                        )
                                    )
                                )
                            )
                            break
                        } else {

                            add(
                                WebviewContentItem.Text(
                                    text.subSequence(
                                        TextRange(
                                            start, end
                                        )
                                    )
                                )
                            )
                        }

                        start = end
                    }
                } while (true)
            }
        }
    }

    LazyColumn(
        modifier = modifier, contentPadding = contentPadding, userScrollEnabled = scrollable
    ) {
        items(strings) { string ->
            when (string) {
                is WebviewContentItem.Image -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(string.url)
                            .crossfade(true).build(),

                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is WebviewContentItem.Text -> {
                    BasicText(
                        text = string.text,
                        style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.primary),
                    )
                }
            }
        }
    }
}

@Deprecated("", ReplaceWith("rememberComicWebViewContent(content, baseUrl, TODO())"))
@Composable
fun rememberAnnotatedString(
    content: String,
    baseUrl: String,
): AnnotatedString = rememberComicWebViewContent(content, baseUrl) {}.text

@Composable
fun rememberComicWebViewContent(
    content: String,
    baseUrl: String,
    onLinkClick: (String) -> Unit = {},
): ComicWebViewContent {
    val body = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.tertiary)
    val title = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.tertiary)
    val headline = MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.tertiary)
    val link = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)


    return remember(baseUrl, body, title, headline, link, onLinkClick) {
        HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY).toAnnotatedString(
            baseUrl,
            body,
            title,
            headline,
            link,
            onLinkClick,
        )
    }
}

private sealed interface WebviewContentItem {
    data class Text(val text: AnnotatedString) : WebviewContentItem

    data class Image(val url: String) : WebviewContentItem
}

data class ComicWebViewContent(val text: AnnotatedString, val images: List<InlineImage>)

data class InlineImage(val url: String, val startIndex: Int, val endIndex: Int)

fun Spanned.toAnnotatedString(
    baseUrl: String,
    body: TextStyle,
    title: TextStyle,
    headline: TextStyle,
    link: TextStyle,
    onLinkClick: (String) -> Unit,
): ComicWebViewContent {
    val list = mutableListOf<InlineImage>()
    val string = buildAnnotatedString {
        Log.w(TAG, "toAnnotatedString: Building string")

        val spanned = this@toAnnotatedString

        var index = 0
        var end: Int
        val skipped = 0
        while (index < spanned.length) {
            end = spanned.nextSpanTransition(index, spanned.length, Any::class.java)

            val spans = spanned.getSpans<Any>(index, end)

            append(spanned.subSequence(index, end))

            val start = index - skipped
            spans.forEach { span ->
                when (span) {
                    is StyleSpan -> when (span.style) {
                        Typeface.BOLD -> addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold), start, end
                        )

                        Typeface.ITALIC -> addStyle(
                            SpanStyle(fontStyle = FontStyle.Italic), start, end
                        )

                        Typeface.BOLD_ITALIC -> addStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
                            ), start, end
                        )
                    }

                    is URLSpan -> {
                        addStyle(
                            link.toSpanStyle(), start, end
                        )
                        val uri = Uri.parse(span.url)
                        val url = if (uri.isAbsolute) uri.toString() else baseUrl + uri.path

                        addLink(
                            url = LinkAnnotation.Url(url, linkInteractionListener = { item ->
                                if (item is LinkAnnotation.Url) {
                                    onLinkClick(item.url.replace("../..", ""))
                                }
                            }),
                            start = start,
                            end = end
                        )
                    }

                    is ImageSpan -> {
                        span.source?.let { list.add(InlineImage(it, start, end)) }
                    }

                    is RelativeSizeSpan -> {
                        addStyle(
                            when (span.sizeChange) {
                                1.2f -> body.toSpanStyle()
                                1.3f -> title.toSpanStyle()
                                1.4f -> headline.toSpanStyle()
                                else -> {
                                    SpanStyle(color = Color.Magenta)
                                }
                            }, start, end
                        )
                    }
                }
            }
            index = end
        }
    }
    return ComicWebViewContent(text = string, images = list)
}