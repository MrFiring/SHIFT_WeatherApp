package ru.mrfiring.shiftweatherapp.domain.theme

class SetDarkThemeUseCase(
    private val repository: ThemeRepository
) {
    operator fun invoke(isDark: Boolean) = repository.setDarkTheme(isDark)
}