package ru.mrfiring.shiftweatherapp.presentation.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.mrfiring.shiftweatherapp.presentation.composables.LoadingIndicator
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError

enum class ApiStatus { LOADING, ERROR, DONE }

@Composable
fun DetailScreen(
    detailComponent: DetailComponent,
) {
    val container by detailComponent.container.observeAsState()
    val status by detailComponent.status.observeAsState()

    Surface {
        Crossfade(targetState = status) { targetState ->
            when (targetState) {
                ApiStatus.LOADING -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }

                ApiStatus.DONE -> {
                    container?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MainParametersCard(it.mainParams)

                            WeatherCard(it.weather)

                            WindCard(domainWind = it.wind)
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        NetworkError(onRetry = detailComponent::onRetryPressed)
                    }
                }
            }
        }
    }
}