package com.keetr.comicsnac.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.keetr.comicsnac.ui.R

val Bangers = FontFamily(Font(R.font.bangers))

val Chewy = FontFamily(Font(R.font.chewy))

// Set of Material typography styles to start with
val Typography = with(Typography()) {
    Typography(
        headlineLarge = headlineLarge.copy(fontFamily = Bangers),
        headlineMedium = headlineMedium.copy(fontFamily = Bangers),
        headlineSmall = headlineSmall.copy(fontFamily = Bangers),
        titleLarge = titleLarge.copy(fontFamily = Chewy),
        titleMedium = titleMedium.copy(fontFamily = Chewy),
        bodyLarge = bodyLarge.copy(fontFamily = Chewy),
        labelMedium = labelMedium.copy(fontFamily = Bangers)
    )
}