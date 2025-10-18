package com.example.todoapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Green = Color(0xFF157F3D)
val White = Color(0xFFFFFFFF)
val Gray = Color(0xFF171717)


/*
*
* Agrega mas colores si necesitamos
* los que son para los textos colocale un on
* onPrimary, onSecondary, tienen que ser hacia
* algo representativo, tu sabras mas o menos
* esta es una mejora para la UI, etc
*
* */
@Immutable
data class AppColors(
    val Primary: Color,
    val Secondary: Color,
    val Background: Color,
    val Surface: Color,
    val onPrimary: Color,
    val onSecondary: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        Primary = Color.Unspecified,
        Secondary = Color.Unspecified,
        Background = Color.Unspecified,
        Surface = Color.Unspecified,
        onPrimary = Color.Unspecified,
        onSecondary = Color.Unspecified
    )
}

/*
*
* Solo puse los 3 colores que tenias pero los demas
* no se que color agregar entonces si sabes que le
* piensas agregar haslo
*
* */
val extendedColors = AppColors(
    Primary = Green,
    Secondary = White,
    Background = Gray,
    Surface = White,
    onPrimary = White,
    onSecondary = White
)

// cuando termines quita los comentarios
