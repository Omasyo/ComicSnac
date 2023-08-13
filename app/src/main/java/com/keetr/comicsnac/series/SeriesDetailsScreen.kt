package com.keetr.comicsnac.series

import android.graphics.drawable.Drawable
import android.text.Html.ImageGetter
import android.text.Html.TagHandler
import android.text.method.LinkMovementMethod
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import java.net.URL

@Composable
fun SeriesDetailsScreen(
    modifier: Modifier = Modifier
) {
    val head = """
            <head>
                <style>
                    @font-face {
                        font-family: Bangers;
                        src: url('file:///android_res/font/bangers.ttf');
                    }
                    h2 {
                        font-family: Bangers;
                        color: red;
                    }
                </style>
            </head>
        """.trimIndent()

    val html = "$head<body>$SampleHtmlText</body>"

//    AndroidView(factory = {context ->
//
//        WebView(context).apply {
//            loadDataWithBaseURL("https://comicvine.gamespot.com/lightning-lad/4005-1253/" ,html, "text/html", "utf-8", null)
//        }
//
//    })

    val webViewState =
        rememberWebViewStateWithHTMLData(html, "https://comicvine.gamespot.com/lightning-lad/4005-1253/")

    WebView(state = webViewState)
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        SeriesDetailsScreen()
    }
}