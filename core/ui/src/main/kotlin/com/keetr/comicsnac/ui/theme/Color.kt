package com.keetr.comicsnac.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val DefaultScheme = generateColorScheme(
    darkColor = Color(0xFF1A323E),
    lightColor = Color(0xFFF7D281),
    surfaceColor = Color(0xFF1B729F),
    altSurfaceColor = Color(0xFF902724),
    error = Color(0xFFF23C27)
)

fun generateColorScheme(
    darkColor: Color,
    lightColor: Color,
    surfaceColor: Color,
    altSurfaceColor: Color,
    error: Color,
) = ColorScheme(
    primary = darkColor,
    onPrimary = lightColor,
    primaryContainer = darkColor,
    onPrimaryContainer = lightColor,
    inversePrimary = lightColor,
    secondary = surfaceColor,
    onSecondary = lightColor,
    secondaryContainer = surfaceColor,
    onSecondaryContainer = lightColor,
    tertiary = altSurfaceColor,
    onTertiary = lightColor,
    tertiaryContainer = altSurfaceColor,
    onTertiaryContainer = lightColor,
    background = darkColor,
    onBackground = lightColor,
    surface = darkColor,
    onSurface = lightColor,
    surfaceVariant = surfaceColor,
    onSurfaceVariant = lightColor,
    surfaceTint = lightColor,
    inverseSurface = lightColor,
    inverseOnSurface = darkColor,
    error = error,
    onError = lightColor,
    errorContainer = error,
    onErrorContainer = lightColor,
    outline = lightColor,
    outlineVariant = lightColor,
    scrim = darkColor,
    )