package ru.mrfiring.shiftweatherapp.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.presentation.composables.CityItem
import ru.mrfiring.shiftweatherapp.presentation.composables.LoadingIndicator
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError
import ru.mrfiring.shiftweatherapp.presentation.composables.ScreenTopBar

@Composable
fun FavoriteScreen(
    favoriteComponent: FavoriteComponent,
    onBackClick: () -> Unit
) {
    val state by favoriteComponent.state.subscribeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTopBar(
            title = stringResource(R.string.screen_favorites),
            onBackClick = onBackClick
        )

        when (val favoriteState = state) {
            is FavoriteState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }

            is FavoriteState.Error -> {
                NetworkError(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    message = stringResource(R.string.error_cities_message),
                    onRetry = favoriteComponent::onRetry
                )
            }

            is FavoriteState.Content -> {
                val favorites = favoriteState.favorites

                if (favorites.isEmpty()) {
                    EmptyFavorites(modifier = Modifier.weight(1f))
                } else {
                    CityList(
                        favorites = favorites,
                        onFavoriteClick = favoriteComponent::onLongPress,
                        onClick = favoriteComponent::onSelectCity,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyFavorites(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_favorites),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CityList(
    favorites: List<City>,
    onFavoriteClick: (City) -> Unit,
    onClick: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(favorites) { index, city ->
            CityItem(
                domainCity = city,
                onFavoriteClick = { onFavoriteClick(city) },
                onClick = { onClick(city) }
            )

            if (index < favorites.lastIndex) {
                Divider(
                    modifier = Modifier.padding(start = 68.dp, end = 6.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 1.dp
                )
            }
        }
    }
}
