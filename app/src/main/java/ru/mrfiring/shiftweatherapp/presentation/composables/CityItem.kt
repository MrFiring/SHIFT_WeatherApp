package ru.mrfiring.shiftweatherapp.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

@Composable
fun CityItem(
    domainCity: City,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .clickable { onClick() }
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.extendedColors.surfaceContainerHigh,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = domainCity.name.take(1).uppercase(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = domainCity.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = getSupportingCityLine(domainCity),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (domainCity.favorite) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarBorder
                },
                contentDescription = null,
                tint = if (domainCity.favorite) {
                    MaterialTheme.extendedColors.primaryStrong
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

private fun getSupportingCityLine(city: City): String =
    if (city.state.isEmpty()) {
        city.country
    } else {
        "${city.state}, ${city.country}"
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
                favorite = true
            ),
            onFavoriteClick = {},
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CityItemWithoutStatePreview() {
    WeatherTheme {
        CityItem(
            domainCity = City(
                id = 1L, name = "London", state = "",
                country = "GB",
                longitude = 0.1,
                latitude = 51.5,
                favorite = false
            ),
            onFavoriteClick = {},
            onClick = {}
        )
    }
}
