package com.keetr.comicsnac.ui.components.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlin.math.roundToInt


@Composable
fun ComicWebview(
    modifier: Modifier = Modifier
) {
    val head = with(MaterialTheme.colorScheme) {
        """
            <head>
                <style>
                    @font-face {
                        font-family: Bangers;
                        src: url('file:///android_res/font/bangers.ttf');
                    }
                    @font-face {
                        font-family: Chewy;
                        src: url('file:///android_res/font/chewy.ttf');
                    }
                    a:link {
                      color: ${secondary.format()};
                    }
                    a:visited {
                      color: ${error.format()};
                    }
                    h2 {
                        font-family: Bangers;
                        color: ${tertiary.format()};
                    }
                    h3 {
                        font-family: Chewy;
                        color: ${tertiary.format()};
                    }
                    body {
                        font-family: Chewy;
                        color: ${primary.format()};
                        background-color: ${onPrimary.format()};
                    }
                </style>
            </head>
        """.trimIndent()
    }

    val html = "$head<body>$SampleHtmlText</body>"

    Color.Red.toString()

    val webViewState =
        rememberWebViewStateWithHTMLData(
            html,
            "https://comicvine.gamespot.com/lightning-lad/4005-1253/"
        )

    WebView(state = webViewState)
}


private fun Color.format() = "#${red.toFF()}${green.toFF()}${blue.toFF()}"

@OptIn(ExperimentalStdlibApi::class)
private fun Float.toFF() = (this * 255).roundToInt().toHexString(HexFormat {
    upperCase = false
    number.removeLeadingZeros = true
}).padStart(2, '0')


@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
            ComicWebview()

//            Column {
//                Text(Color.Red.value.toString())
//                Text(Color.Black.toArgb().toString())
//                Text(Color.Black.format())
//                Text(0x000000.toString())
//            }
        }
    }
}