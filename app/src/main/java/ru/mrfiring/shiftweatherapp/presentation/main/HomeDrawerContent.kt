package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R

@Composable
fun HomeDrawerContent(
    onChangeThemeClick: () -> Unit,
    onCitiesClick: () -> Unit,
    onCountriesClick: () -> Unit,
    onFavoritesClick: () -> Unit,
) {
    Column {
        HomeDrawerHeader(onClick = onChangeThemeClick)

        Divider(modifier = Modifier.padding(bottom = 8.dp))

        DrawerMenuItem(
            painter = painterResource(R.drawable.ic_city_icon),
            text = "Show cities",
            onClick = onCitiesClick
        )

        DrawerMenuItem(
            painter = painterResource(R.drawable.ic_country_icon),
            text = "Show countries",
            onClick = onCountriesClick
        )

        DrawerMenuItem(
            painter = painterResource(R.drawable.ic_favorite),
            text = "Favorites",
            onClick = onFavoritesClick
        )

        DrawerMenuItem(
            painter = painterResource(R.drawable.ic_settings),
            text = "Settings",
            onClick = { /*TODO*/ }
        )
    }
}