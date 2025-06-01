package ru.mrfiring.shiftweatherapp.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mrfiring.shiftweatherapp.domain.onboarding.SetOnboardingPassedUseCase
import ru.mrfiring.shiftweatherapp.presentation.SingleLiveEvent

data class OnboardingState(
    val locationGranted: Boolean
)

class OnboardingViewModel(
    private val setOnboardingPassedUseCase: SetOnboardingPassedUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<OnboardingState>()
    val state: LiveData<OnboardingState> = _state

    private val _goNextEvent = SingleLiveEvent<Unit>()
    val goNextEvent: LiveData<Unit> = _goNextEvent

    private val _locationAvailableEvent = SingleLiveEvent<Unit>()
    val locationAvailableEvent: LiveData<Unit> = _locationAvailableEvent

    fun handleInitialLocationGranted(granted: Boolean) {
        _state.value = OnboardingState(granted)
    }

    fun handleLocationRequestResult(granted: Boolean) {
        _state.value = OnboardingState(granted)

        if (granted) {
            setOnboardingPassedUseCase()
            _locationAvailableEvent.call()
        }
    }

    fun goNext() {
        val locationGranted = requireNotNull(_state.value).locationGranted

        setOnboardingPassedUseCase()

        if (locationGranted) {
            _locationAvailableEvent.call()
        } else {
            _goNextEvent.call()
        }
    }
}