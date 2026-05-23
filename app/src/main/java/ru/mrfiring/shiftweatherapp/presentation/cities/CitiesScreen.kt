package ru.mrfiring.shiftweatherapp.presentation.cities


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ru.mrfiring.shiftweatherapp.presentation.composables.CityItem
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowNetworkError

@ExperimentalPagingApi
@Composable
fun CitiesScreen(
    citiesComponent: CitiesComponent,
) {
    val lazyPagingItems = citiesComponent.cities.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(count = lazyPagingItems.itemCount) {
                val city = lazyPagingItems[it]
                if (city != null) {
                    CityItem(
                        domainCity = city,
                        onLongTap = {
                            citiesComponent.onLongPress(city)
                        }, onClick = { citiesComponent.onSelectCity(city) })
                }
            }

            //Show loading state by appending item to the LazyColumn
            lazyPagingItems.apply {
                //Only check refresh state. Others are minor.
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        item {
                            ShowLoading(modifier = Modifier.fillMaxSize())
                        }
                    }
                    is LoadState.Error -> {
                        item {
                            ShowNetworkError(
                                modifier = Modifier.fillMaxSize(),
                                onRetry = lazyPagingItems::retry
                            )
                        }
                    }
                    //Do nothing
                    else -> {
                    }
                }
            }
        }
    }
}
