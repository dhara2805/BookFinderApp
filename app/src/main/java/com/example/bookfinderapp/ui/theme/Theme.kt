package com.example.bookfinderapp.ui.theme


import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable



private val LightColorPalette = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF1E88E5),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF1976D2),
    secondary = androidx.compose.ui.graphics.Color(0xFFFFC107)
)
@Composable
fun BookFinderTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = androidx.compose.material.Typography(),
        shapes = androidx.compose.material.Shapes(),
        content = content
    )
}