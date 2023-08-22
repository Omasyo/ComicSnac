package com.keetr.comicsnac.ui.components.webview

import android.graphics.Typeface
import android.net.Uri
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

const val TAG = "SComicWebView"

@OptIn(ExperimentalTextApi::class)
@Composable
fun ComicWebView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0f.dp),
    data: String,
    baseUrl: String,
    scrollable: Boolean = true,
    onLinkClick: (String) -> Unit,
) {

    val body = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.tertiary)
    val title = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.tertiary)
    val headline = MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.tertiary)
    val link = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)

    var annotatedString by remember(body, title, headline, link) {
        mutableStateOf(AnnotatedString(""))
    }

    LaunchedEffect(body, title, headline, link) {
        this.launch(Dispatchers.IO) {
            annotatedString = HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toAnnotatedString(
                    baseUrl,
                    body,
                    title,
                    headline,
                    link
                )
        }
    }

    with(MaterialTheme) {
        Crossfade(targetState = annotatedString) { annotatedString ->
            ClickableText(
                text = annotatedString,
                style = typography.bodyLarge.copy(colorScheme.primary),
                modifier = modifier
                    .verticalScroll(
                        rememberScrollState(),
                        scrollable
                    )
                    .padding(contentPadding)
            ) { offset ->
                annotatedString.getUrlAnnotations(offset, offset).firstOrNull()?.let {
                    onLinkClick(it.item.url)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .fillMaxSize()
                .padding(24f.dp)
        ) {
            ComicWebView(
                Modifier.fillMaxSize(),
                data = SampleHtmlText, baseUrl =
                "https://comicvine.gamespot.com/lightning-lad/4005-1253/"
            )
            { Log.d("TAG", "ComicWebView: $it") }

//            Column {
//                Text(Color.Red.value.toString())
//                Text(Color.Black.toArgb().toString())
//                Text(Color.Black.format())
//                Text(0x000000.toString())
//            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
fun Spanned.toAnnotatedString(
    baseUrl: String,
    body: TextStyle,
    title: TextStyle,
    headline: TextStyle,
    link: TextStyle
): AnnotatedString =
    buildAnnotatedString {
        val spanned = this@toAnnotatedString

        var index = 0
        var end: Int
        var skipped = 0
        while (index < spanned.length) {
            end = spanned.nextSpanTransition(index, spanned.length, Any::class.java)

            val spans = spanned.getSpans<Any>(index, end)

//            if (!spans.any { span -> span is ImageSpan }) {
            append(spanned.subSequence(index, end))
//            } else {
//                ++skipped
//            }

            val start = index - skipped
            spans.forEach { span ->

                when (span) {
                    is StyleSpan -> when (span.style) {
                        Typeface.BOLD -> addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start,
                            end
                        )

                        Typeface.ITALIC -> addStyle(
                            SpanStyle(fontStyle = FontStyle.Italic),
                            start,
                            end
                        )

                        Typeface.BOLD_ITALIC -> addStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            ), start, end
                        )
                    }

                    is URLSpan -> {
                        addStyle(
                            link.toSpanStyle(),
                            start,
                            end
                        )
                        addUrlAnnotation(UrlAnnotation(baseUrl + span.url), start, end)
                    }

                    is ImageSpan -> {

                        val source = span.source
                        if (source != null) {
//                            appendInlineContent(source)
                        }
                    }

                    is RelativeSizeSpan -> {
                        addStyle(
                            when (val size = span.sizeChange) {
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


@Preview
@Composable
fun InlinePreview() {
    MaterialTheme {
        Surface {
            val inlineContent = mapOf(
                "boxId" to InlineTextContent(
                    Placeholder(
                        16f.em,
                        16f.em,
                        PlaceholderVerticalAlign.Center
                    )
                ) {
                    Box(
                        Modifier
                            .background(Color.Red.copy(0.2f))
                            .fillMaxSize()
                    )
                }
            )
            val annotatedString = buildAnnotatedString {
                append(List(100) { "String" }.joinToString(" "))
                appendInlineContent("boxId")
                append(List(100) { "String" }.joinToString(" "))
            }
            Text(text = annotatedString, inlineContent = inlineContent)
        }
    }
}