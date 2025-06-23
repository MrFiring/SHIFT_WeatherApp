package ru.mrfiring.shiftweatherapp.presentation.countries

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.composables.ThemeAwareCard

@ExperimentalFoundationApi
@Composable
fun CountriesScreen(
    countriesComponent: CountriesComponent,
) {
    val countries by countriesComponent.countries.observeAsState(initial = listOf(""))

    if (countries.isEmpty()) {
        ShowLoading(Modifier.fillMaxSize())
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 108.dp)
        ) {
            items(countries) { country ->
                ThemeAwareCard(
                    modifier = Modifier
                        .size(128.dp, 100.dp)
                        .padding(4.dp)
                        .clickable {
                            countriesComponent.onCountryClick(country)
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = country)
                    }
                }
            }
        }
    }

}