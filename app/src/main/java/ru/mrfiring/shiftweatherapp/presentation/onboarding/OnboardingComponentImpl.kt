package ru.mrfiring.shiftweatherapp.presentation.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import ru.mrfiring.shiftweatherapp.domain.onboarding.SetOnboardingPassedUseCase

interface OnboardingComponent {

    val state: Value<OnboardingState>

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


    private val _state = MutableValue(OnboardingState(locationGranted = false))
    override val state: Value<OnboardingState> = _state

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