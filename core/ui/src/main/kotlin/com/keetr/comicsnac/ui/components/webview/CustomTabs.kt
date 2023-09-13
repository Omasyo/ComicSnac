package com.keetr.comicsnac.ui.components.webview

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.toArgb

fun openUrl(context: Context, url: String, colorScheme: ColorScheme) {
    val intent = CustomTabsIntent.Builder().run {
        val params = CustomTabColorSchemeParams.Builder().run {
            setNavigationBarColor(colorScheme.primary.toArgb())
            setNavigationBarDividerColor(colorScheme.onSurface.toArgb())
            setToolbarColor(colorScheme.tertiary.toArgb())
            build()
        }
        setDefaultColorSchemeParams(params)
        build()
    }

    intent.launchUrl(context, Uri.parse(url))
}