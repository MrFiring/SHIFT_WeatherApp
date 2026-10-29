package ru.mrfiring.shiftweatherapp.presentation.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.composables.CountryTileSkeleton
import ru.mrfiring.shiftweatherapp.presentation.composables.ScreenTopBar
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

// Пропорции плитки страны из макета (ширина:высота = 128:100).
private const val COUNTRY_TILE_ASPECT_RATIO = 128f / 100f

@Composable
fun CountriesScreen(
    countriesComponent: CountriesComponent,
    onBackClick: () -> Unit,
    onSwitchToCities: () -> Unit
) {
    val countries by countriesComponent.countries.subscribeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTopBar(
            title = stringResource(R.string.screen_countries),
            onBackClick = onBackClick,
            action = {
                IconButton(onClick = onSwitchToCities) {
                    Icon(
                        imageVector = Icons.Rounded.LocationCity,
                        contentDescription = null
                    )
                }
            }
        )

        if (countries.isEmpty()) {
            CountriesLoadingSkeleton(modifier = Modifier.weight(1f))
        } else {
            CountriesGrid(
                countries = countries,
                onCountryClick = countriesComponent::onCountryClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CountriesGrid(
    countries: List<String>,
    onCountryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(countries) { country ->
            CountryTile(
                country = country,
                onCountryClick = onCountryClick
            )
        }
    }
}

@Composable
private fun CountryTile(
    country: String,
    onCountryClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(COUNTRY_TILE_ASPECT_RATIO)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.extendedColors.surfaceContainer)
            .clickable { onCountryClick(country) },
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text = country,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun CountriesLoadingSkeleton(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.extendedColors.surfaceContainerHigh
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(8) {
                CountryTileSkeleton()
            }
        }
    }
}
