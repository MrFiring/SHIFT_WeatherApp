package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.DomainDrawerMenuItem
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.composables.AppBar
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerContent
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerHeader
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
            HomeDrawerHeader(onClick = mainComponent::onChangeThemeClick)
            Divider(modifier = Modifier.padding(bottom = 8.dp))
            HomeDrawerContent(
                listOf(
                    DomainDrawerMenuItem(
                        drawableId = R.drawable.ic_city_icon,
                        text = "Show cities",
                        onClick = mainComponent::onCitiesClick
                    ),
                    DomainDrawerMenuItem(
                        drawableId = R.drawable.ic_country_icon,
                        text = "Show countries",
                        onClick = mainComponent::onCountriesClick
                    ),
                    DomainDrawerMenuItem(
                        drawableId = R.drawable.ic_favorite,
                        text = "Favorites",
                        onClick = mainComponent::onFavoritesClick
                    ),
                    DomainDrawerMenuItem(
                        drawableId = R.drawable.ic_settings,
                        text = "Settings",
                        onClick = { /*TODO*/ }
                    )
                )
            )
        }
    ) { paddingValues ->
        Children(mainComponent.stack, modifier = Modifier.padding(paddingValues)) {
            when (val child = it.instance) {
                is Child.Cities -> CitiesScreen(child.component)
                is Child.Countries -> CountriesScreen(child.component)
                is Child.Detail -> DetailScreen(child.component)
                is Child.Favorite -> FavoriteScreen(child.component)
            }
        }
    }
}