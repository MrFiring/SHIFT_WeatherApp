package ru.mrfiring.shiftweatherapp.presentation.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme

@Composable
fun CityItem(domainCity: City, onLongTap: (Offset) -> Unit = {}, onClick: (Offset) -> Unit) {
    ThemeAwareCard(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) { //To determine longPress and tap.
                detectTapGestures(
                    onLongPress = onLongTap,
                    onTap = onClick
                )
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = domainCity.name, style = MaterialTheme.typography.h5)
            Text(text = domainCity.country, style = MaterialTheme.typography.h6)
        }
    }
}

@Preview
@Composable
private fun CityItemPreview() {
    WeatherTheme {
        CityItem(
            domainCity = City(
                id = 0L, name = "New York", state = "NY",
                country = "US",
                longitude = 2.0,
                latitude = 2.0,
                favorite = false,
            ),
            onClick = {}
        )
    }
}