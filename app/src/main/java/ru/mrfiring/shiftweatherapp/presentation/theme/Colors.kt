package ru.mrfiring.shiftweatherapp.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Colors.primarySurface: Color get() = if (isLight) primary else surface

val Grey900 = Color(0xff222222)
val Grey800 = Color(0xff484848)
val GreyLight = Color(0xff393939)
val DarkTextColor = Color(0xffd4d4d4)
val Black = Color(0xff000000)

val Orange900 = Color(0xffB33E21)
val Orange800 = Color(0xffEB6E4B)
val LightBack = Color(0xffF5F5F5)

internal val DarkColors = darkColors(
    primary = Orange800,
    primaryVariant = Orange900,
    onPrimary = Color.White,
    secondary = GreyLight,
    secondaryVariant = Black,
    onSecondary = DarkTextColor,
    background = Grey900,
    surface = Grey800
)

internal val LightColors = lightColors(
    primary = Orange800,
    primaryVariant = Orange900,
    onPrimary = Color.White,
    secondary = Orange800,
    secondaryVariant = Orange900,
    onSecondary = Color.White,
    background = Color.White,
    surface = LightBack
)