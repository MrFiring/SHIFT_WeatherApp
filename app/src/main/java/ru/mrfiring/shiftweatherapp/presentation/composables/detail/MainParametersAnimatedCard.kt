package ru.mrfiring.shiftweatherapp.presentation.composables.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.DomainMainWeatherParameters
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth


@Composable
fun MainParametersAnimatedCard(domainMainWeatherParameters: DomainMainWeatherParameters) {
    CardWithPaddingAndFillWidth {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val curTemp =
                stringResource(id = R.string.temp_format, domainMainWeatherParameters.temp)
            val feelsLike = stringResource(
                id = R.string.temp_feels_format,
                domainMainWeatherParameters.feelsLike
            )
            val minTemp =
                stringResource(id = R.string.temp_format, domainMainWeatherParameters.tempMin)
            val maxTemp =
                stringResource(id = R.string.temp_format, domainMainWeatherParameters.tempMax)
            val humidity =
                stringResource(id = R.string.humidity_format, domainMainWeatherParameters.humidity)
            val pressure =
                stringResource(id = R.string.pressure_format, domainMainWeatherParameters.pressure)
            Text(
                text = curTemp,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h4
            )
            Text(
                text = feelsLike,
                modifier = Modifier.padding(),
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Min: $minTemp",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Blue
                    )
                    Text(
                        text = "Max: $maxTemp",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Red
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = humidity,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.subtitle1,

                        )
                    Text(
                        text = pressure,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.subtitle1,

                        )
                }
            }
        }

    }

}