package ru.mrfiring.shiftweatherapp.presentation.composables.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage
import ru.mrfiring.shiftweatherapp.data.network.IMG_URL
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeather
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth

@Composable
fun WeatherCard(domainWeather: DomainWeather) {
    CardWithPaddingAndFillWidth {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val url = IMG_URL.format(domainWeather.icon)
            GlideImage(
                data = url,
                contentDescription = "Weather image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(0.dp, 8.dp)
            )

            Text(
                text = domainWeather.main,
                style = MaterialTheme.typography.h5
            )

            Text(
                text = domainWeather.description,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.subtitle1
            )

        }
    }
}