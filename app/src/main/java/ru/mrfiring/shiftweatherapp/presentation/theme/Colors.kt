package ru.mrfiring.shiftweatherapp.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Grey900 = Color(0xff222222)
val Grey800 = Color(0xff484848)
val GreyLight = Color(0xff393939)
val DarkTextColor = Color(0xffd4d4d4)

val Orange900 = Color(0xffB33E21)
val Orange800 = Color(0xffEB6E4B)
val LightBack = Color(0xffF5F5F5)

internal val DarkColors = darkColors(
    primary = Grey900,
    primaryVariant = Color.Black,
    onPrimary = DarkTextColor,
    secondary = GreyLight,
    secondaryVariant = Color.Black,
    onSecondary = DarkTextColor,
    background = Grey900,
    surface = Grey900
)

internal val LightColors = lightColors(
    primary = Orange800,
    primaryVariant = Orange900,
    onPrimary = Color.Black,
    secondary = Orange800,
    secondaryVariant = Color.White,
    onSecondary = Color.Black,
    background = Color.White,
    surface = LightBack
)