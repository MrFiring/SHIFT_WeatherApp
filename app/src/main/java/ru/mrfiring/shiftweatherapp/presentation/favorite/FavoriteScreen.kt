package ru.mrfiring.shiftweatherapp.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.mrfiring.shiftweatherapp.presentation.composables.CityItem
import ru.mrfiring.shiftweatherapp.presentation.composables.LoadingIndicator
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus


@Composable
fun FavoriteScreen(
    favoriteComponent: FavoriteComponent
) {
    val favorites by favoriteComponent.favorites.subscribeAsState()
    val status by favoriteComponent.status.subscribeAsState()

    if (favorites.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.testTag("NoFavoritesText"),
                text = "There isn't any favorite city.\nYou can add a city with long tap on it."
            )
        }
    } else {
        LazyColumn(Modifier.testTag("FavoriteLazy")) {

            items(favorites) { city ->
                CityItem(
                    domainCity = city,
                    onLongTap = {
                        favoriteComponent.onLongPress(city)
                    },
                    onClick = {
                        favoriteComponent.onSelectCity(city)
                    })
            }

            favorites.apply {
                when (status) {
                    ApiStatus.LOADING -> {
                        item {
                            LoadingIndicator()
                        }
                    }
                    ApiStatus.ERROR -> {
                        item {
                            NetworkError(onRetry = favoriteComponent::onRetry)
                        }
                    }
                    else -> {
                    }

                }
            }


        }
    }
}