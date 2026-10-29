package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.WbCloudy
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponentImpl.Child

@Composable
fun HomeDrawerContent(
    selectedChild: Child,
    darkTheme: Boolean,
    onWeatherNowClick: () -> Unit,
    onCitiesClick: () -> Unit,
    onCountriesClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onChangeThemeClick: () -> Unit,
) {
    Column {
        HomeDrawerHeader()

        DrawerMenuItem(
            icon = Icons.Rounded.WbCloudy,
            text = stringResource(R.string.drawer_home),
            selected = selectedChild is Child.Detail,
            onClick = onWeatherNowClick
        )

        DrawerMenuItem(
            icon = Icons.Rounded.LocationCity,
            text = stringResource(R.string.screen_cities),
            selected = selectedChild is Child.Cities,
            onClick = onCitiesClick
        )

        DrawerMenuItem(
            icon = Icons.Rounded.Public,
            text = stringResource(R.string.screen_countries),
            selected = selectedChild is Child.Countries,
            onClick = onCountriesClick
        )

        DrawerMenuItem(
            icon = Icons.Rounded.Star,
            text = stringResource(R.string.screen_favorites),
            selected = selectedChild is Child.Favorite,
            onClick = onFavoritesClick
        )

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )

        DarkThemeRow(
            darkTheme = darkTheme,
            onCheckedChange = onChangeThemeClick
        )
    }
}

@Composable
private fun DarkThemeRow(
    darkTheme: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 14.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.DarkMode,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = stringResource(R.string.drawer_dark_theme),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = darkTheme,
            onCheckedChange = { onCheckedChange() }
        )
    }
}
