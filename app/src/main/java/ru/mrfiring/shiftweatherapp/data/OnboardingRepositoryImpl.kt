package ru.mrfiring.shiftweatherapp.data

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.mrfiring.shiftweatherapp.domain.onboarding.OnboardingRepository

class OnboardingRepositoryImpl(private val preferences: SharedPreferences) : OnboardingRepository {

    private companion object {

        const val KEY = "onboarding_passed_key"
    }

    override fun setPassed() {
        preferences.edit {
            putBoolean(KEY, true)
        }
    }

    override fun isPassed(): Boolean =
        preferences.getBoolean(KEY, false)
}