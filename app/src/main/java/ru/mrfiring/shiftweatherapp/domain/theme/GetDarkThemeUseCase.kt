package ru.mrfiring.shiftweatherapp.domain.theme

class GetDarkThemeUseCase(
    private val repository: ThemeRepository
) : () -> Boolean by repository::getDarkTheme