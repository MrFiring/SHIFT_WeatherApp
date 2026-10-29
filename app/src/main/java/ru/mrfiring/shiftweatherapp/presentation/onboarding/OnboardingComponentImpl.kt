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
    fun selectPage(pageIndex: Int)
    fun skipRequested()
    fun startRequested()
}

class OnboardingComponentImpl(
    private val componentContext: ComponentContext,
    private val setOnboardingPassedUseCase: SetOnboardingPassedUseCase,
    private val locationAvailableHandler: () -> Unit,
    private val goNextHandler: () -> Unit,
) : OnboardingComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        OnboardingState(
            page = OnboardingPage.WELCOME,
            locationGranted = false
        )
    )
    override val state: Value<OnboardingState> = _state

    override fun handleInitialLocationGranted(granted: Boolean) {
        if (granted) {
            _state.value = OnboardingState(page = OnboardingPage.DONE, locationGranted = true)
        }
    }

    override fun handleLocationRequestResult(granted: Boolean) {
        if (granted) {
            setOnboardingPassedUseCase()
            _state.value = OnboardingState(page = OnboardingPage.DONE, locationGranted = true)
        }
    }

    override fun goNext() {
        val currentPage = _state.value.page

        _state.value = _state.value.copy(
            page = OnboardingPage.entries.getOrElse(currentPage.ordinal + 1) { currentPage }
        )
    }

    override fun selectPage(pageIndex: Int) {
        val currentPage = _state.value.page

        _state.value = _state.value.copy(
            page = OnboardingPage.entries.getOrElse(pageIndex) { currentPage }
        )
    }

    override fun skipRequested() {
        _state.value = _state.value.copy(page = OnboardingPage.DONE)
    }

    override fun startRequested() {
        val locationGranted = _state.value.locationGranted

        setOnboardingPassedUseCase()

        if (locationGranted) {
            locationAvailableHandler()
        } else {
            goNextHandler()
        }
    }
}
