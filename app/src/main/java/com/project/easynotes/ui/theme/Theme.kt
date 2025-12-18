package com.project.easynotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = SoftBlue,
    onPrimary = Color.White,
    primaryContainer = MintGreen,
    onPrimaryContainer = TextPrimary,
    secondary = DarkGreen,
    onSecondary = Color.White,
    secondaryContainer = SoftYellow,
    onSecondaryContainer = TextPrimary,
    background = BackgroundWhite,
    onBackground = TextPrimary,
    surface = BackgroundWhite,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundGray,
    onSurfaceVariant = TextSecondary
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2D5F4F),
    onPrimaryContainer = MintGreen,
    secondary = DarkGreen,
    onSecondary = Color.White,
    background = Color(0xFF1A1A1A),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF2D2D2D),
    onSurface = Color(0xFFE0E0E0)
)

@Composable
fun EasyNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
