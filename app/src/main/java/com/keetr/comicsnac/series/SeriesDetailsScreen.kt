package com.keetr.comicsnac.series

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

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