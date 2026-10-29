package ru.mrfiring.shiftweatherapp.presentation.cities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.composables.CityItem
import ru.mrfiring.shiftweatherapp.presentation.composables.CityItemSkeleton
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError
import ru.mrfiring.shiftweatherapp.presentation.composables.ScreenTopBar
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

@ExperimentalPagingApi
@Composable
fun CitiesScreen(
    citiesComponent: CitiesComponent,
    onBackClick: () -> Unit,
    onSwitchToCountries: () -> Unit
) {
    val lazyPagingItems = citiesComponent.cities.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTopBar(
            title = stringResource(R.string.screen_cities),
            onBackClick = onBackClick,
            action = {
                IconButton(onClick = onSwitchToCountries) {
                    Icon(
                        imageVector = Icons.Rounded.Public,
                        contentDescription = null
                    )
                }
            }
        )

        val refreshState = lazyPagingItems.loadState.refresh

        when {
            refreshState is LoadState.Loading && lazyPagingItems.itemCount == 0 -> {
                CitiesLoadingSkeleton(modifier = Modifier.weight(1f))
            }

            refreshState is LoadState.Error -> {
                NetworkError(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    message = stringResource(R.string.error_cities_message),
                    onRetry = lazyPagingItems::retry
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = lazyListState,
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(count = lazyPagingItems.itemCount) { index ->
                        val city = lazyPagingItems[index]
                        if (city != null) {
                            CityItem(
                                domainCity = city,
                                onFavoriteClick = { citiesComponent.onLongPress(city) },
                                onClick = { citiesComponent.onSelectCity(city) }
                            )

                            if (index < lazyPagingItems.itemCount - 1) {
                                Divider(
                                    modifier = Modifier.padding(start = 68.dp, end = 6.dp),
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CitiesLoadingSkeleton(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.extendedColors.surfaceContainerHigh
        )

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            repeat(6) {
                CityItemSkeleton()
            }
        }
    }
}
