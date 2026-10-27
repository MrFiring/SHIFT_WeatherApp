package ru.mrfiring.shiftweatherapp.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.presentation.composables.CityItem
import ru.mrfiring.shiftweatherapp.presentation.composables.LoadingIndicator
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError


@Composable
fun FavoriteScreen(
    favoriteComponent: FavoriteComponent
) {
    val state by favoriteComponent.state.subscribeAsState()

    when (state) {
        is FavoriteState.Loading -> {
            LoadingIndicator()
        }

        is FavoriteState.Error -> {
            NetworkError(onRetry = favoriteComponent::onRetry)
        }

        is FavoriteState.Content -> {
            val favorites = (state as FavoriteState.Content).favorites

            if (favorites.isEmpty()) {
                EmptyFavorites()
            } else {
                CityList(
                    favorites = favorites,
                    onLongTap = favoriteComponent::onLongPress,
                    onClick = favoriteComponent::onSelectCity
                )
            }
        }
    }
}

@Composable
private fun EmptyFavorites() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.no_favorites))
    }
}

@Composable
private fun CityList(
    favorites: List<City>,
    onLongTap: (City) -> Unit,
    onClick: (City) -> Unit
) {
    LazyColumn {
        items(favorites) { city ->
            CityItem(
                domainCity = city,
                onLongTap = { onLongTap(city) },
                onClick = { onClick(city) }
            )
        }
    }
}