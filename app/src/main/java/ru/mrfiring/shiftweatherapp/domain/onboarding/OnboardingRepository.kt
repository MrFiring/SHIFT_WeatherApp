package ru.mrfiring.shiftweatherapp.domain.onboarding

interface OnboardingRepository {

    fun setPassed()

    fun isPassed(): Boolean
}