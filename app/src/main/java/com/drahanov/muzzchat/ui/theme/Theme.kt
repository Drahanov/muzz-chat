package com.drahanov.muzzchat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MuzzColorScheme = lightColorScheme(
    primary = MuzzPink,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Navy,
    surface = Color.White,
    onSurface = Navy,
    surfaceVariant = BubbleGrey,
    onSurfaceVariant = Slate,
    outline = BorderGrey,
    outlineVariant = HeaderGrey,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainer = Color.White,
    surfaceContainerHigh = Color.White,
    surfaceContainerHighest = BubbleGrey,
)

@Composable
fun MuzzChatTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MuzzColorScheme,
        typography = Typography,
        content = content,
    )
}
