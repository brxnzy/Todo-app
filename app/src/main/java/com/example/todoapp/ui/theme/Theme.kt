package com.example.todoapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun TodoTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors provides extendedColors,
        LocalAppTypography provides extendedTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object TodoTheme {
    val colors: AppColors
        @Composable get() = LocalAppColors.current
    val typography: AppTypography
        @Composable get() = LocalAppTypography.current
}