//package com.keetr.comicsnac.ui.components.webview
//
//import android.graphics.Canvas
//import android.graphics.Typeface
//import android.graphics.drawable.BitmapDrawable
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.text.Html
//import android.text.Spanned
//import android.text.style.ForegroundColorSpan
//import android.text.style.StyleSpan
//import android.text.style.URLSpan
//import android.text.style.UnderlineSpan
//import android.text.util.Linkify
//import android.util.Log
//import android.widget.TextView
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.scrollable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.toArgb
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.fontResource
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.ExperimentalTextApi
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.UrlAnnotation
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.res.ResourcesCompat
//import androidx.core.text.HtmlCompat
//import coil.ImageLoader
//import coil.request.ImageRequest
//import com.google.accompanist.web.WebContent
//import com.google.accompanist.web.WebStateSaver
//import com.google.accompanist.web.WebView
//import com.google.accompanist.web.WebViewState
//import com.keetr.comicsnac.ui.R
//import com.keetr.comicsnac.ui.theme.ComicSnacTheme
//import kotlin.math.roundToInt
//
//const val TAG = "SComicWebView"
//
//private class DrawablePlaceHolder : BitmapDrawable() {
//
//    private var drawable: Drawable? = null
//
//    override fun draw(canvas: Canvas) {
//        drawable?.draw(canvas)
//    }
//
//    fun updateDrawable(drawable: Drawable) {
//        this.drawable = drawable
//        val width = drawable.intrinsicWidth
//        val height = drawable.intrinsicHeight
//        drawable.setBounds(0, 0, width, height)
//        setBounds(0, 0, width, height)
//    }
//}
//
//@Composable
//fun ComicWebView(
//    modifier: Modifier = Modifier,
//    data: String,
//    baseUrl: String,
//    onLinkClick: (Uri) -> Unit,
//) {
//    val context = LocalContext.current
//
//
//    val html = "$Head<body>$data</body>"
//
//    val webViewState = rememberSaveable(saver = WebStateSaver) {
//        WebViewState(WebContent.Data(html, baseUrl))
//    }
//
//    Text(
//        text = HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_LEGACY).toAnnotatedString(),
//
//        modifier = modifier.verticalScroll(rememberScrollState())
//    )
//
////    AndroidView(
////        modifier = modifier.verticalScroll(rememberScrollState()),
////        factory = { context ->
////            TextView(context).apply {
////                val typeface = ResourcesCompat.getFont(context, R.font.chewy)
////
////                setTypeface(typeface)
//////                font
////
////                autoLinkMask = Linkify.WEB_URLS
////                linksClickable = true
////                // setting the color to use forr highlihting the links
////                setLinkTextColor(Color.White.toArgb())
////            }
////        }
////    ) { textView ->
////        textView.text = HtmlCompat.fromHtml(
////            data,
////            HtmlCompat.FROM_HTML_MODE_LEGACY,
////            {source ->
////
////                ImageRequest.Builder(context).data(source).target {
////
////
////                }
////
////
////                val drawablePlaceholder = DrawablePlaceHolder()
////                ImageLoader(context).enqueue(ImageRequest.Builder(context).data(source).apply {
////                    target { drawable ->
////                        drawablePlaceholder.updateDrawable(drawable)
////                        // invalidating the drawable doesn't seem to be enough...
////                        textView.text = textView.text
////                    }
////                }.build())
////                // Since this loads async, we return a "blank" drawable, which we update
////                // later
////
////                Log.d(TAG, "ComicWebView: Source: $source")
////                drawablePlaceholder
////            },
////            { opening, tag, output, reader ->
////                Log.d(TAG, "ComicWebView: Opening $opening")
////                Log.d(TAG, "ComicWebView: Tag $tag")
////                Log.d(TAG, "ComicWebView: Output $output")
////                Log.d(TAG, "ComicWebView: Reader $reader")
////            }
////        )
////
////    }
//}
//
//private val Head
//    @Composable get() = with(MaterialTheme.colorScheme) {
//        """
//            <head>
//                <style>
//                    @font-face {
//                        font-family: Bangers;
//                        src: url('file:///android_res/font/bangers.ttf');
//                    }
//                    @font-face {
//                        font-family: Chewy;
//                        src: url('file:///android_res/font/chewy.ttf');
//                    }
//                    a:link {
//                      color: ${secondary.format()};
//                    }
//                    a:visited {
//                      color: ${error.format()};
//                    }
//                    h2 {
//                        font-family: Bangers;
//                        color: ${tertiary.format()};
//                    }
//                    h3 {
//                        font-family: Chewy;
//                        color: ${tertiary.format()};
//                    }
//                    body {
//                        font-family: Chewy;
//                        color: ${primary.format()};
//                        background-color: ${onPrimary.format()};
//                    }
//                </style>
//            </head>
//        """.trimIndent()
//    }
//
//private val Css  //TODO: Couldn't get this to work
//    @Composable get() = with(MaterialTheme.colorScheme) {
//        """
//            @font-face {
//                font-family: Bangers;
//                src: url('file:///android_res/font/bangers.ttf');
//            }
//            @font-face {
//                font-family: Chewy;
//                src: url('file:///android_res/font/chewy.ttf');
//            }
//            a:link {
//              color: ${secondary.format()};
//            }
//            a:visited {
//              color: ${error.format()};
//            }
//            h2 {
//                font-family: Bangers;
//                color: ${tertiary.format()};
//            }
//            h3 {
//                font-family: Chewy;
//                color: ${tertiary.format()};
//            }
//            body {
//                font-family: Chewy;
//                color: ${primary.format()};
//                background-color: ${onPrimary.format()};
//            }
//        """.trimIndent()
//    }
//
//private fun Color.format() = "#${red.toFF()}${green.toFF()}${blue.toFF()}"
//
//@OptIn(ExperimentalStdlibApi::class)
//private fun Float.toFF() = (this * 255).roundToInt().toHexString(HexFormat {
//    upperCase = false
//    number.removeLeadingZeros = true
//}).padStart(2, '0')
//
//
//@Preview
//@Composable
//private fun Preview() {
//    ComicSnacTheme {
//        Box(
//            Modifier
//                .background(MaterialTheme.colorScheme.surface)
//                .fillMaxSize()
//                .padding(24f.dp)
//        ) {
//            ComicWebView(
//                Modifier.fillMaxSize(),
//                data = SampleHtmlText, baseUrl =
//                "https://comicvine.gamespot.com/lightning-lad/4005-1253/"
//            )
//            { Log.d("TAG", "ComicWebView: $it") }
//
////            Column {
////                Text(Color.Red.value.toString())
////                Text(Color.Black.toArgb().toString())
////                Text(Color.Black.format())
////                Text(0x000000.toString())
////            }
//        }
//    }
//}
//
//@OptIn(ExperimentalTextApi::class)
//fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
//    val spanned = this@toAnnotatedString
//    append(spanned.toString())
//    getSpans(0, spanned.length, Any::class.java).forEach { span ->
//        val start = getSpanStart(span)
//        val end = getSpanEnd(span)
//        when (span) {
//            is StyleSpan -> when (span.style) {
//                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
//                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
//                Typeface.BOLD_ITALIC -> addStyle(
//                    SpanStyle(
//                        fontWeight = FontWeight.Bold,
//                        fontStyle = FontStyle.Italic
//                    ), start, end
//                )
//            }
//
//            is UnderlineSpan -> addStyle(
//                SpanStyle(textDecoration = TextDecoration.Underline),
//                start,
//                end
//            )
//            is URLSpan -> { addStyle(
//                SpanStyle(textDecoration = TextDecoration.Underline),
//                start,
//                end
//            )
//                addUrlAnnotation(UrlAnnotation(span.url), start, end)
//            }
//
//            is ForegroundColorSpan -> addStyle(
//                SpanStyle(color = Color(span.foregroundColor)),
//                start,
//                end
//            )
//        }
//    }
//}