package ru.mrfiring.shiftweatherapp.domain.onboarding

class IsOnboardingPassedUseCase(
    private val repository: OnboardingRepository
) : () -> Boolean by repository::isPassed