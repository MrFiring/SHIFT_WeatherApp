package ru.mrfiring.shiftweatherapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.domain.models.Weather
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth

@Composable
fun WeatherCard(domainWeather: Weather) {
    CardWithPaddingAndFillWidth {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = domainWeather.main,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = domainWeather.description,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}