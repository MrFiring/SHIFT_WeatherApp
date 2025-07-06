package ru.mrfiring.shiftweatherapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.mrfiring.shiftweatherapp.presentation.RootComponent.Child
import ru.mrfiring.shiftweatherapp.presentation.main.MainScreen
import ru.mrfiring.shiftweatherapp.presentation.onboarding.OnboardingScreen

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is Child.Main -> MainScreen(child.component)
            is Child.Onboarding -> OnboardingScreen(child.component)
        }
    }
}