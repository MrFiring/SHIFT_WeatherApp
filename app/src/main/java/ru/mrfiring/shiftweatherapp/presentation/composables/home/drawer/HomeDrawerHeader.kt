package ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mrfiring.shiftweatherapp.R

@Composable
fun HomeDrawerHeader(onClick: () -> Unit) {

    val isLight = MaterialTheme.colors.isLight

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        val switcher = painterResource(id = R.drawable.ic_theme_switcher)
        Image(
            painter = switcher,
            contentDescription = "Switch theme",
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 8.dp)
                .clickable { onClick() }
                .background(
                    color = if (isLight) {
                        MaterialTheme.colors.surface
                    } else {
                        MaterialTheme.colors.secondary
                    }, shape = CircleShape
                ),

            colorFilter = if (isLight) {
                ColorFilter.tint(MaterialTheme.colors.primary)
            } else {
                ColorFilter.tint(MaterialTheme.colors.onPrimary)
            }

        )
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "User avatar",

            )

        Column(
            modifier = Modifier.padding(vertical = 14.dp)
        ) {
            Text(
                text = "Unknown user",
                style = MaterialTheme.typography.h5,
                color = if (isLight) MaterialTheme.colors.secondaryVariant
                else MaterialTheme.colors.onPrimary
            )
            Text(
                text = "hello1234@shiftweather.ru",
                style = MaterialTheme.typography.subtitle1,
                fontSize = 18.sp,
                color = if (isLight) MaterialTheme.colors.secondaryVariant
                else MaterialTheme.colors.onPrimary
            )
        }

    }

}