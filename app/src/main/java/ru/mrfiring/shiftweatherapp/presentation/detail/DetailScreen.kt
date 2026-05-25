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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.mrfiring.shiftweatherapp.presentation.composables.LoadingIndicator
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError

enum class ApiStatus { LOADING, ERROR, DONE }

@Composable
fun DetailScreen(
    detailComponent: DetailComponent,
) {
    val state by detailComponent.state.subscribeAsState()

    Surface {
        Crossfade(targetState = state) { targetState ->
            when (targetState) {
                is DetailState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }

                is DetailState.Content -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MainParametersCard(targetState.container.mainParams)

                        WeatherCard(targetState.container.weather)

                        WindCard(domainWind = targetState.container.wind)
                    }
                }

                is DetailState.Error -> {
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