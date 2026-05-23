package ru.mrfiring.shiftweatherapp.presentation.composables.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.DomainMainWeatherParameters
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme


@Composable
fun MainParametersCard(domainMainWeatherParameters: DomainMainWeatherParameters) {
    CardWithPaddingAndFillWidth {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTemperature(
                current = domainMainWeatherParameters.temp,
                feelsLike = domainMainWeatherParameters.feelsLike
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TemperatureRange(
                    min = domainMainWeatherParameters.tempMin,
                    max = domainMainWeatherParameters.tempMax
                )

                SecondaryWeatherInfo(
                    humidity = domainMainWeatherParameters.humidity,
                    pressure = domainMainWeatherParameters.pressure
                )
            }
        }
    }
}

@Composable
private fun MainTemperature(
    current: Double,
    feelsLike: Double
) {
    val currentTemp = stringResource(id = R.string.temp_format, current)
    val feelsLikeTemp = stringResource(id = R.string.temp_feels_format, feelsLike)

    Text(
        text = currentTemp,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        style = MaterialTheme.typography.h4
    )

    Text(
        text = feelsLikeTemp,
        modifier = Modifier.padding(bottom = 8.dp),
        style = MaterialTheme.typography.h6
    )
}

@Composable
private fun TemperatureRange(
    min: Double,
    max: Double
) {
    val minTemp = stringResource(id = R.string.temp_format, min)
    val maxTemp = stringResource(id = R.string.temp_format, max)

    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Min: $minTemp",
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = "Max: $maxTemp",
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.error
        )
    }
}

@Composable
private fun SecondaryWeatherInfo(
    humidity: Double,
    pressure: Int
) {
    val humidityText = stringResource(id = R.string.humidity_format, humidity)
    val pressureText = stringResource(id = R.string.pressure_format, pressure)

    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = humidityText,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )

        Text(
            text = pressureText,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainParametersCardPreview() {
    WeatherTheme {
        MainParametersCard(
            domainMainWeatherParameters = DomainMainWeatherParameters(
                temp = 25.0,
                feelsLike = 27.0,
                tempMin = 20.0,
                tempMax = 30.0,
                pressure = 1013,
                humidity = 65.0,
                pressureAtSeaLevel = 1013,
                pressureAtGroundLevel = 1015
            )
        )
    }
}