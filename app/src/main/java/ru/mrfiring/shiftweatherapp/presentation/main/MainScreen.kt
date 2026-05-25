package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.composables.AppBar
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesScreen
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreen
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponentImpl.Child

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagingApi::class)
@Composable
fun MainScreen(mainComponent: MainComponent) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = "Weather",
                onNavigationClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            HomeDrawerContent(
                onChangeThemeClick = mainComponent::onChangeThemeClick,
                onCitiesClick = mainComponent::onCitiesClick,
                onCountriesClick = mainComponent::onCountriesClick,
                onFavoritesClick = mainComponent::onFavoritesClick,
            )
        }
    ) { paddingValues ->
        Children(
            stack = mainComponent.stack,
            modifier = Modifier.padding(paddingValues),
            animation = stackAnimation()
        ) {
            when (val child = it.instance) {
                is Child.Cities -> CitiesScreen(child.component)
                is Child.Countries -> CountriesScreen(child.component)
                is Child.Detail -> DetailScreen(child.component)
                is Child.Favorite -> FavoriteScreen(child.component)
            }
        }
    }
}