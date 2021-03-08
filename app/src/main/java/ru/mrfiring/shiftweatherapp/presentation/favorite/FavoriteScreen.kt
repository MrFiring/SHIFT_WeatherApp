package ru.mrfiring.shiftweatherapp.presentation.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import ru.mrfiring.shiftweatherapp.di.getViewModel
import ru.mrfiring.shiftweatherapp.presentation.Navigations
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowNetworkError
import ru.mrfiring.shiftweatherapp.presentation.composables.home.CityItem
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus


@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteScreenViewModel = getViewModel()
) {
    val favorites by viewModel.favorites.observeAsState(initial = emptyList())
    val status by viewModel.status.observeAsState(initial = ApiStatus.LOADING)

    Column(modifier = Modifier.fillMaxWidth()) {

        LazyColumn {

            items(favorites) { city ->
                CityItem(domainCity = city, onClick = {
                    navController.navigate("${Navigations.Details}/${city.id}")
                })
            }

            favorites.apply {
                when (status) {
                    ApiStatus.LOADING -> {
                        item {
                            ShowLoading()
                        }
                    }
                    ApiStatus.ERROR -> {
                        item {
                            ShowNetworkError(onRetry = viewModel::onRetry)
                        }
                    }
                    else -> {
                        //Do nothing)
                    }

                }
            }
        }


    }
}