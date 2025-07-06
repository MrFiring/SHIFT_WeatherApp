package ru.mrfiring.shiftweatherapp.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.mrfiring.shiftweatherapp.di.ComponentFactory
import ru.mrfiring.shiftweatherapp.di.createMainComponent
import ru.mrfiring.shiftweatherapp.di.createOnboardingComponent
import ru.mrfiring.shiftweatherapp.domain.onboarding.IsOnboardingPassedUseCase
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponent
import ru.mrfiring.shiftweatherapp.presentation.onboarding.OnboardingComponent

class RootComponent(
    componentContext: ComponentContext,
    private val locationGranted: Boolean,
    private val themeChangeHandler: () -> Unit,
    private val isOnboardingPassedUseCase: IsOnboardingPassedUseCase,
    private val componentFactory: ComponentFactory,
) : ComponentContext by componentContext {

    private val navigation: StackNavigation<Config> = StackNavigation()

    val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = getInitialConfiguration(),
        handleBackButton = true,
        childFactory = ::child
    )

    private fun getInitialConfiguration(): Config =
        if (isOnboardingPassedUseCase()) {
            Config.Main(locationGranted)
        } else {
            Config.Onboarding
        }

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Main -> Child.Main(
                componentFactory.createMainComponent(
                    componentContext,
                    locationGranted = config.locationGranted,
                    changeThemeHandler = themeChangeHandler::invoke,
                )
            )

            Config.Onboarding -> Child.Onboarding(
                componentFactory.createOnboardingComponent(
                    componentContext,
                    locationAvailableHandler = { navigation.replaceCurrent(Config.Main(locationGranted = true)) },
                    goNextHandler = { navigation.replaceCurrent(Config.Main(locationGranted = false)) }
                )
            )
        }

    sealed class Child {

        class Main(val component: MainComponent) : Child()
        class Onboarding(val component: OnboardingComponent) : Child()
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data class Main(val locationGranted: Boolean) : Config

        @Serializable
        data object Onboarding : Config
    }
}

