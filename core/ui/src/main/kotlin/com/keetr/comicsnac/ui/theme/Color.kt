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

val DarkKnightScheme = generateColorScheme(
    primary = Color(0xFF191919),
    onSurface = Color(0xFFCFB830),
    secondary = Color(0xFF434858),
    tertiary = Color(0xFF232824),
    error = Color(0xFFF04712)
)

val IronManScheme = generateColorScheme(
    primary = Color(0xFF6A0C0B),
    onSurface = Color(0xFFE6D283),
    secondary = Color(0xFFB17302),
    tertiary = Color(0xFF646768),
    error = Color(0xFF307C99)
)

val DoggyBagsScheme = generateColorScheme(
    primary = Color(0xFF921A0A),
    onSurface = Color(0xFFF6E65F),
    secondary = Color(0xFF663715),
    tertiary = Color(0xFF3B5752),
    error = Color(0xFFF81E41)
)

val SpawnScheme = generateColorScheme(
    primary = Color(0xFF141D25),
    onSurface = Color(0xFFC9C7BE),
    secondary = Color(0xFF783D2B),
    tertiary = Color(0xFFB41C17),
    error = Color(0xFF3B7901)
)

val AnotherScheme = generateColorScheme(
    primary = Color(0xFF562C2C),
    onSurface = Color(0xFFF5DFBB),
    secondary = Color(0xFFF2542D),
    tertiary = Color(0xFF127475),
    error = Color(0xFF0E9594)
)

val YetAnotherScheme = generateColorScheme(
    primary = Color(0xFF3B1F2B),
    onSurface = Color(0xFFDBDFAC),
    secondary = Color(0xFFDB162F),
    tertiary = Color(0xFF383961),
    error = Color(0xFF5F758E)
)

val LightScheme = generateColorScheme(
    primary = Color(0xFF8A7E72),
    onSurface = Color(0xFF5A2328),
    secondary = Color(0xFF997A96),
    tertiary = Color(0xFF7A9B76),
    error = Color(0xFF090302)
)


val ColorSchemes = mapOf(
    0 to DefaultScheme,
    1 to DarkKnightScheme,
    2 to IronManScheme,
    3 to DoggyBagsScheme,
    4 to SpawnScheme,
    5 to AnotherScheme,
    6 to YetAnotherScheme,
    7 to LightScheme
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