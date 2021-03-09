package ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(
                    color = if (isLight) {
                        MaterialTheme.colors.surface
                    } else {
                        MaterialTheme.colors.secondary
                    }, shape = CircleShape
                )
                .align(Alignment.End)
                .padding(8.dp)
                .size(32.dp, 30.dp)
        ) {
            Icon(
                painter = switcher,
                contentDescription = "Switch theme",
                tint = if (isLight) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onPrimary
                }
            )
        }
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