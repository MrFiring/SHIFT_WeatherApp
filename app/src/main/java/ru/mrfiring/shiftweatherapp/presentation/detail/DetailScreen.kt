package ru.mrfiring.shiftweatherapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import org.koin.core.parameter.parametersOf
import ru.mrfiring.shiftweatherapp.di.getViewModel
import ru.mrfiring.shiftweatherapp.presentation.ShowAppBar
import ru.mrfiring.shiftweatherapp.presentation.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.ShowNetworkError
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
    Column() {
        ShowAppBar(title = "Weather Details")
        when (status) {
            ApiStatus.LOADING -> {
                ShowLoading()
            }
            ApiStatus.DONE -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    MainParametersAnimatedCard(params!!.mainParams)
                    WeatherCard(params!!.weather)
                    WindCard(domainWind = params!!.wind)
                }
            }
            else -> {
                ShowNetworkError(viewModel::onRetryPressed)
            }

        }
    }


}