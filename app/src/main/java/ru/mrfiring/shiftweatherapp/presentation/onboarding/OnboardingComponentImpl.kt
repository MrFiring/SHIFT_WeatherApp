package ru.mrfiring.shiftweatherapp.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkivanov.decompose.ComponentContext
import ru.mrfiring.shiftweatherapp.domain.onboarding.SetOnboardingPassedUseCase

interface OnboardingComponent {

    val state: LiveData<OnboardingState>

    fun handleInitialLocationGranted(granted: Boolean)
    fun handleLocationRequestResult(granted: Boolean)

    fun goNext()
}

class OnboardingComponentImpl(
    private val componentContext: ComponentContext,
    private val setOnboardingPassedUseCase: SetOnboardingPassedUseCase,
    private val locationAvailableHandler: () -> Unit,
    private val goNextHandler: () -> Unit,
) : OnboardingComponent, ComponentContext by componentContext {


    private val _state = MutableLiveData<OnboardingState>()
    override val state: LiveData<OnboardingState> = _state

    override fun handleInitialLocationGranted(granted: Boolean) {
        _state.value = OnboardingState(granted)
    }

    override fun handleLocationRequestResult(granted: Boolean) {
        _state.value = OnboardingState(granted)

        if (granted) {
            setOnboardingPassedUseCase()
            locationAvailableHandler()
        }
    }

    override fun goNext() {
        val locationGranted = requireNotNull(_state.value).locationGranted

        setOnboardingPassedUseCase()

        if (locationGranted) {
            locationAvailableHandler()
        } else {
            goNextHandler()
        }
    }
}