package ru.mrfiring.shiftweatherapp.presentation.onboarding

data class OnboardingState(
    val page: OnboardingPage,
    val locationGranted: Boolean
)

enum class OnboardingPage(val pageIndex: Int) {
    WELCOME(0),
    CITIES(1),
    PERMISSION(2),
    DONE(3)
}
