package ru.mrfiring.shiftweatherapp.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesScreen
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreen
import ru.mrfiring.shiftweatherapp.presentation.onboarding.OnboardingScreen

@OptIn(ExperimentalPagingApi::class, ExperimentalFoundationApi::class)
@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Cities -> CitiesScreen(child.component)
            is RootComponent.Child.Countries -> CountriesScreen(child.component)
            is RootComponent.Child.Detail -> DetailScreen(child.component)
            is RootComponent.Child.Favorite -> FavoriteScreen(child.component)
            is RootComponent.Child.Onboarding -> OnboardingScreen(child.component)
        }
    }
}