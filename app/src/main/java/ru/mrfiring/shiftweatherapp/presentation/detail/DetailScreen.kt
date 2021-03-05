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
    navController: NavController,
    cityId: Long,
    viewModel: DetailViewModel = getViewModel { parametersOf(cityId) }
) {
    val params by viewModel.container.observeAsState()
    val status by viewModel.status.observeAsState()
    Column {
        ShowAppBar(title = "Weather Details")
        when (status) {
            ApiStatus.LOADING -> {
                ShowLoading(modifier = Modifier.fillMaxSize())
            }
            ApiStatus.DONE -> {
                params?.let {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        MainParametersAnimatedCard(params!!.mainParams)
                        WeatherCard(params!!.weather)
                        WindCard(domainWind = params!!.wind)
                    }
                }
            }
            else -> {
                ShowNetworkError(
                    modifier = Modifier.fillMaxSize(),
                    onRetry = viewModel::onRetryPressed
                )
            }

        }
    }


}