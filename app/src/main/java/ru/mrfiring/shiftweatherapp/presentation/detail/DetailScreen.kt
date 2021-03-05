package ru.mrfiring.shiftweatherapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.core.parameter.parametersOf
import ru.mrfiring.shiftweatherapp.di.getViewModel
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowAppBar
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowNetworkError
import ru.mrfiring.shiftweatherapp.presentation.composables.detail.MainParametersAnimatedCard
import ru.mrfiring.shiftweatherapp.presentation.composables.detail.WeatherCard
import ru.mrfiring.shiftweatherapp.presentation.composables.detail.WindCard

enum class ApiStatus { LOADING, ERROR, DONE }

@Composable
fun DetailScreen(
    navController: NavController, //For the future things)
    cityId: Long,
    viewModel: DetailViewModel = getViewModel { parametersOf(cityId) }
) {
    val container by viewModel.container.observeAsState()
    val status by viewModel.status.observeAsState()
    Column {
        ShowAppBar(title = "Weather Details")

        //Check the status of data and show content or state of the load
        when (status) {
            //Show progress bar
            ApiStatus.LOADING -> {
                ShowLoading(modifier = Modifier.fillMaxSize())
            }
            //Elements are loaded successfully then show them
            ApiStatus.DONE -> {
                container?.let {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        MainParametersAnimatedCard(it.mainParams)
                        WeatherCard(it.weather)
                        WindCard(domainWind = it.wind)
                    }
                }
            }
            //Show network error message with retry button
            else -> {
                ShowNetworkError(
                    modifier = Modifier.fillMaxSize(),
                    onRetry = viewModel::onRetryPressed
                )
            }

        }
    }


}