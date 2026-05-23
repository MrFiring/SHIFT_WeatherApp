package ru.mrfiring.shiftweatherapp.data

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.mrfiring.shiftweatherapp.domain.theme.ThemeRepository

class ThemeRepositoryImpl(private val preferences: SharedPreferences) : ThemeRepository {

    private companion object {

        const val KEY = "theme_key"
        const val DEFAULT = true
    }

    override fun getDarkTheme(): Boolean =
        preferences.getBoolean(KEY, DEFAULT)

    override fun setDarkTheme(isDark: Boolean) {
        preferences.edit {
            putBoolean(KEY, isDark)
        }
    }

    override fun clearTheme() {
        preferences.edit {
            remove(KEY)
        }
    }
}