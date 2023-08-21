//package com.keetr.comicsnac.ui.components.webview
//
//import android.net.Uri
//import android.webkit.WebResourceRequest
//import android.webkit.WebView
//import com.google.accompanist.web.AccompanistWebViewClient
//
//
//internal class OComicWebViewClient(
//    private val onLinkClick: (Uri) -> Unit
//) : AccompanistWebViewClient() {
//
//    override fun onPageFinished(view: WebView, url: String?) {
//        super.onPageFinished(view, url)
//        view.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
//    }
//
//    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//        request?.url?.let {
//            onLinkClick(it)
//        }
//        return true
//    }
//}