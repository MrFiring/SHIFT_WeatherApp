package ru.mrfiring.shiftweatherapp.domain.theme

interface ThemeRepository {

    fun getDarkTheme(): Boolean?

    fun setDarkTheme(isDark: Boolean)

    fun clearTheme()
}