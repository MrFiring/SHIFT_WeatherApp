package ru.mrfiring.shiftweatherapp.presentation.composables.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.domain.DomainWind

@Composable
fun WindCard(domainWind: DomainWind) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Direction of wind is ${domainWind.degToWord()}"
            )
            Text(
                text = "Speed: ${domainWind.speed}",
                modifier = Modifier.padding(4.dp)
            )
            if (domainWind.gust > 0) {
                Text(
                    text = "Gust up to ${domainWind.gust}"
                )
            }

        }
    }
}