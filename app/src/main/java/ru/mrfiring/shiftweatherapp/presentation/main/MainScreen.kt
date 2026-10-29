package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesScreen
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreen
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponentImpl.Child

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagingApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainComponent: MainComponent, darkTheme: Boolean) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val childStack by mainComponent.stack.subscribeAsState()
    val activeChild = childStack.active.instance

    fun closeDrawer() {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                HomeDrawerContent(
                    selectedChild = activeChild,
                    darkTheme = darkTheme,
                    onWeatherNowClick = {
                        mainComponent.onWeatherNowClick()
                        closeDrawer()
                    },
                    onCitiesClick = {
                        mainComponent.onCitiesClick()
                        closeDrawer()
                    },
                    onCountriesClick = {
                        mainComponent.onCountriesClick()
                        closeDrawer()
                    },
                    onFavoritesClick = {
                        mainComponent.onFavoritesClick()
                        closeDrawer()
                    },
                    onChangeThemeClick = mainComponent::onChangeThemeClick,
                )
            }
        }
    ) {
        Children(
            stack = mainComponent.stack,
            animation = stackAnimation()
        ) {
            when (val child = it.instance) {
                is Child.Cities -> CitiesScreen(
                    citiesComponent = child.component,
                    onBackClick = mainComponent::onBackClick,
                    onSwitchToCountries = mainComponent::onCountriesClick
                )

                is Child.Countries -> CountriesScreen(
                    countriesComponent = child.component,
                    onBackClick = mainComponent::onBackClick,
                    onSwitchToCities = mainComponent::onCitiesClick
                )

                is Child.Favorite -> FavoriteScreen(
                    favoriteComponent = child.component,
                    onBackClick = mainComponent::onBackClick
                )

                is Child.Detail -> DetailScreen(
                    detailComponent = child.component,
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    onCitiesClick = mainComponent::onCitiesClick,
                    onThemeToggleClick = mainComponent::onChangeThemeClick
                )
            }
        }
    }
}
