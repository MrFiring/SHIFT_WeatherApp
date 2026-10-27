package ru.mrfiring.shiftweatherapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ShiftExtendedColors(
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val primaryStrong: Color,
    val scrim: Color,
    val clear: Color,
    val clearInk: Color,
    val partly: Color,
    val partlyInk: Color,
    val cloudy: Color,
    val cloudyInk: Color,
    val rain: Color,
    val rainInk: Color,
    val sleet: Color,
    val sleetInk: Color,
    val snow: Color,
    val snowInk: Color,
    val fog: Color,
    val fogInk: Color,
    val storm: Color,
    val stormInk: Color,
    val ok: Color,
    val warn: Color,
    val danger: Color
)

val LightExtendedColors = ShiftExtendedColors(
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFCFAF8),
    surfaceContainer = Color(0xFFF6F3F0),
    surfaceContainerHigh = Color(0xFFF0ECE9),
    surfaceContainerHighest = Color(0xFFEAE5E2),
    primaryStrong = Color(0xFF9F3700),
    scrim = Color(0x70050302),
    clear = Color(0xFFEC8049),
    clearInk = Color(0xFF9F440B),
    partly = Color(0xFFA89C92),
    partlyInk = Color(0xFF5F5650),
    cloudy = Color(0xFF98918B),
    cloudyInk = Color(0xFF56504B),
    rain = Color(0xFF347CBA),
    rainInk = Color(0xFF0B5388),
    sleet = Color(0xFF5A8AA9),
    sleetInk = Color(0xFF235776),
    snow = Color(0xFF92BFD2),
    snowInk = Color(0xFF305F72),
    fog = Color(0xFFB5B0AC),
    fogInk = Color(0xFF5C5550),
    storm = Color(0xFF647AB9),
    stormInk = Color(0xFF344575),
    ok = Color(0xFF287C42),
    warn = Color(0xFF966800),
    danger = Color(0xFFAC0003)
)

val DarkExtendedColors = ShiftExtendedColors(
    surfaceContainerLowest = Color(0xFF110F0E),
    surfaceContainerLow = Color(0xFF23211F),
    surfaceContainer = Color(0xFF2B2826),
    surfaceContainerHigh = Color(0xFF353230),
    surfaceContainerHighest = Color(0xFF403C39),
    primaryStrong = Color(0xFFFCA581),
    scrim = Color(0x70050302),
    clear = Color(0xFFEC8049),
    clearInk = Color(0xFF9F440B),
    partly = Color(0xFFA89C92),
    partlyInk = Color(0xFF5F5650),
    cloudy = Color(0xFF98918B),
    cloudyInk = Color(0xFF56504B),
    rain = Color(0xFF347CBA),
    rainInk = Color(0xFF0B5388),
    sleet = Color(0xFF5A8AA9),
    sleetInk = Color(0xFF235776),
    snow = Color(0xFF92BFD2),
    snowInk = Color(0xFF305F72),
    fog = Color(0xFFB5B0AC),
    fogInk = Color(0xFF5C5550),
    storm = Color(0xFF647AB9),
    stormInk = Color(0xFF344575),
    ok = Color(0xFF287C42),
    warn = Color(0xFF966800),
    danger = Color(0xFFFF655A)
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

val MaterialTheme.extendedColors: ShiftExtendedColors
    @Composable get() = LocalExtendedColors.current
