package ru.mrfiring.shiftweatherapp.presentation.countries

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.chrisbanes.accompanist.glide.GlideImage
import ru.mrfiring.shiftweatherapp.data.network.FLAG_URL
import ru.mrfiring.shiftweatherapp.di.getViewModel
import ru.mrfiring.shiftweatherapp.presentation.Navigations
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.composables.ThemeAwareCard

@ExperimentalFoundationApi
@Composable
fun CountriesScreen(
    navController: NavController,
    viewModel: CountriesScreenViewModel = getViewModel()
) {
    val countries by viewModel.countries.observeAsState(initial = listOf(""))

    if (countries.isNullOrEmpty()) {
        ShowLoading(Modifier.fillMaxSize())
    } else {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 108.dp)
        ) {
            items(countries) { country ->
                val url = FLAG_URL.format(country)
                ThemeAwareCard(
                    modifier = Modifier
                        .size(128.dp, 100.dp)
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(route = "${Navigations.Cities}/?country=$country")
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GlideImage(
                            data = url,
                            contentDescription = null
                        )

                    }

                }
            }
        }
    }

}