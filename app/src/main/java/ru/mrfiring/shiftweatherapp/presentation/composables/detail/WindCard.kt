package ru.mrfiring.shiftweatherapp.presentation.composables.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.DomainWind
import ru.mrfiring.shiftweatherapp.presentation.theme.ThemeAwareCard

@Composable
fun WindCard(domainWind: DomainWind) {
    ThemeAwareCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val formattedDirection =
                stringResource(id = R.string.direction_format, domainWind.degToWord())
            val formattedSpeed = stringResource(id = R.string.speed_format, domainWind.speed)
            Text(
                text = formattedDirection
            )
            Text(
                text = formattedSpeed,
                modifier = Modifier.padding(4.dp)
            )
            if (domainWind.gust > 0) {
                val formattedGust = stringResource(id = R.string.gust_format, domainWind.gust)
                Text(
                    text = formattedGust
                )
            } else {
                Text(text = stringResource(id = R.string.no_gust))
            }

        }
    }
}