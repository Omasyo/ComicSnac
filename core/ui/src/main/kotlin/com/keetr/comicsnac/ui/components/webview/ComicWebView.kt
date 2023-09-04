package com.keetr.comicsnac.ui.components.webview

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans

const val TAG = "SComicWebView"

@OptIn(ExperimentalTextApi::class)
@Composable
fun ComicWebView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0f.dp),
    annotatedString: AnnotatedString,
    scrollable: Boolean = true,
    onLinkClick: (String) -> Unit,
) {
    val strings = remember {
        buildList {
            var start = 0
            var end = annotatedString.indexOf('\n', start + 400)

            while (end >= 0) {
                add(annotatedString.subSequence(TextRange(start, end)))
                start = end
                end = annotatedString.indexOf('\n', start + 400)
//                Log.i(TAG, "ComicWebView: start is $start end is $end ${annotatedString.length}")
            }
            add(annotatedString.subSequence(start, annotatedString.length))
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        userScrollEnabled = scrollable
    ) {
        items(strings) { string ->
            ClickableText(
                text = string,
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.primary),
            ) { offset ->
                string.getUrlAnnotations(offset, offset).firstOrNull()?.let {
                    onLinkClick(it.item.url.replace("../..", ""))
                }
            }
        }
    }
}


@Composable
fun rememberAnnotatedString(content: String, baseUrl: String): AnnotatedString {
    val body =
        MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.tertiary)
    val title =
        MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.tertiary)
    val headline =
        MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.tertiary)
    val link =
        MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)


    return remember(body, title, headline, link) {
        HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            .toAnnotatedString(
                baseUrl,
                body,
                title,
                headline,
                link
            )
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
        Log.w(TAG, "toAnnotatedString: Building string")

        val spanned = this@toAnnotatedString

        var index = 0
        var end: Int
        val skipped = 0
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
