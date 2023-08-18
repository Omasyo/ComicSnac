package com.keetr.comicsnac.ui.components.webview

import android.net.Uri
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient


class ComicWebViewClient(
    private val onLinkClick: (Uri) -> Unit
) : AccompanistWebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        request?.url?.let {
            onLinkClick(it)
        }
        return true
    }
}