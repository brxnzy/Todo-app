package com.example.todoapp.ui.theme


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todoapp.R


private val inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

@Immutable
data class AppTypography(
    val headline: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val label: TextStyle
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        headline = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        body = TextStyle.Default,
        bodySmall = TextStyle.Default,
        label = TextStyle.Default
    )
}

val extendedTypography = AppTypography(
    headline = TextStyle(
        fontFamily = inter,
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal
    ),
    titleLarge = TextStyle(
        fontFamily = inter,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    titleMedium = TextStyle(
        fontFamily = inter,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    titleSmall = TextStyle(
        fontFamily = inter,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    body = TextStyle(
        fontFamily = inter,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = inter,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    label = TextStyle(
        fontFamily = inter,
        fontSize = 11.sp,
        fontWeight = FontWeight.Light
    )

)


// te estoy cargando la fuente tuya de una mejor forma
// quita estos comentarios