package com.keetr.comicsnac.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val DefaultScheme = generateColorScheme(
    primary = Color(0xFF1A323E),
    onSurface = Color(0xFFF7D281),
    secondary = Color(0xFF1B729F),
    tertiary = Color(0xFF902724),
    error = Color(0xFFF23C27)
)

fun generateColorScheme(
    primary: Color,
    onSurface: Color,
    secondary: Color,
    tertiary: Color,
    error: Color,
) = ColorScheme(
    primary = primary,
    onPrimary = onSurface,
    primaryContainer = primary,
    onPrimaryContainer = onSurface,
    inversePrimary = onSurface,
    secondary = secondary,
    onSecondary = onSurface,
    secondaryContainer = secondary,
    onSecondaryContainer = onSurface,
    tertiary = tertiary,
    onTertiary = onSurface,
    tertiaryContainer = tertiary,
    onTertiaryContainer = onSurface,
    background = primary,
    onBackground = onSurface,
    surface = primary,
    onSurface = onSurface,
    surfaceVariant = Color(0xFF050A0C),
    onSurfaceVariant = Color(0xFFD2DBDF),
    surfaceTint = onSurface,
    inverseSurface = onSurface,
    inverseOnSurface = primary,
    error = error,
    onError = onSurface,
    errorContainer = error,
    onErrorContainer = onSurface,
    outline = onSurface,
    outlineVariant = onSurface,
    scrim = primary,
    )