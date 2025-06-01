package ru.mrfiring.shiftweatherapp.domain.onboarding

class SetOnboardingPassedUseCase(
    private val repository: OnboardingRepository
) : () -> Unit by repository::setPassed