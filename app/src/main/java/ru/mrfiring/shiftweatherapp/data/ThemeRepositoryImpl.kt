package ru.mrfiring.shiftweatherapp.data

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.mrfiring.shiftweatherapp.domain.theme.ThemeRepository

class ThemeRepositoryImpl(private val preferences: SharedPreferences) : ThemeRepository {

    private companion object {

        const val KEY = "theme_key"
    }

    override fun getDarkTheme(): Boolean? =
        if (preferences.contains(KEY)) {
            preferences.getBoolean(KEY, false)
        } else {
            null
        }

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