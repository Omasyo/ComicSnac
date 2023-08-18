package com.keetr.comicsnac.ui.components.webview

import android.util.Log
import android.webkit.ConsoleMessage
import com.google.accompanist.web.AccompanistWebChromeClient

internal class ComicWebChromeClient : AccompanistWebChromeClient() {
    private val tag = this::class.simpleName

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d(
            tag,
            "${consoleMessage?.messageLevel()} ${consoleMessage?.message()} -- From line " +
                    "${consoleMessage?.lineNumber()} of ${consoleMessage?.sourceId()}"
        )
        return true
    }
}