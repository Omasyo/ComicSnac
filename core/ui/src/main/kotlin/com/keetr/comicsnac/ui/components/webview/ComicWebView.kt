package com.keetr.comicsnac.ui.components.webview

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebStateSaver
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import kotlin.math.roundToInt

@Composable
fun ComicWebView(
    modifier: Modifier = Modifier,
    data: String,
    baseUrl: String,
    onLinkClick: (Uri) -> Unit,
) {
    val html = "$Head<body>$data</body>"

    val webViewState = rememberSaveable(saver = WebStateSaver) {
        WebViewState(WebContent.Data(html, baseUrl))
    }

    WebView(
        modifier = modifier,
        state = webViewState,
        client = ComicWebViewClient(onLinkClick),
        chromeClient = ComicWebChromeClient()
    )
}

private val Head
    @Composable get() = with(MaterialTheme.colorScheme) {
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

private val Css  //TODO: Couldn't get this to work
    @Composable get() = with(MaterialTheme.colorScheme) {
        """
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
        """.trimIndent()
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
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(24f.dp)
        ) {
            ComicWebView(
                data = SampleHtmlText, baseUrl =
                "https://comicvine.gamespot.com/lightning-lad/4005-1253/"
            )
            { Log.d("TAG", "ComicWebView: $it") }

            Column {
                Text(Color.Red.value.toString())
                Text(Color.Black.toArgb().toString())
                Text(Color.Black.format())
                Text(0x000000.toString())
            }
        }
    }
}