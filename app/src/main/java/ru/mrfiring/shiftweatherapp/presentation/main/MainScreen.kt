package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.paging.ExperimentalPagingApi

import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesScreen
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreen
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponentImpl.Child

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagingApi::class)
@Composable
fun MainScreen(mainComponent: MainComponent) {
    Children(mainComponent.stack) {
        when (val child = it.instance) {
            is Child.Cities -> CitiesScreen(child.component)
            is Child.Countries -> CountriesScreen(child.component)
            is Child.Detail -> DetailScreen(child.component)
            is Child.Favorite -> FavoriteScreen(child.component)
        }
    }
}