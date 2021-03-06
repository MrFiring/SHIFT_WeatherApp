package ru.mrfiring.shiftweatherapp.presentation.cities


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.koin.core.parameter.parametersOf
import ru.mrfiring.shiftweatherapp.di.getViewModel
import ru.mrfiring.shiftweatherapp.presentation.Navigations
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowNetworkError
import ru.mrfiring.shiftweatherapp.presentation.composables.ThemeAwareCard
import ru.mrfiring.shiftweatherapp.presentation.composables.home.CityItem

@ExperimentalPagingApi
@Composable
fun CitiesScreen(
    navController: NavController,
    country: String = "",
    viewModel: CitiesScreenViewModel = getViewModel { parametersOf(country) }
) {
    val lazyPagingItems = viewModel.cities.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            LazyColumn(state = lazyListState) {
                items(lazyPagingItems) {
                    it?.let { city ->
                        CityItem(
                            domainCity = city,
                            onLongTap = {
                                viewModel.onLongPress(city)
                            }
                        ) {
                            navController.navigate("${Navigations.Details}/${city.id}") {
                                //Need to navigateUp correctly on a real device
                                popUpTo(Navigations.Cities) { inclusive = false }
                            }
                        }
                    }
                }

                //Show loading state by appending item to the LazyColumn
                lazyPagingItems.apply {
                    //Only check refresh state. Others are minor.
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            item {
                                ThemeAwareCard(modifier = Modifier.fillMaxWidth()) {
                                    ShowLoading(modifier = Modifier.fillMaxSize())
                                }
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
}
