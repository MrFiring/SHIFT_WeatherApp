package ru.mrfiring.shiftweatherapp.presentation.main

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
import ru.mrfiring.shiftweatherapp.presentation.theme.primarySurface

@Composable
fun HomeDrawerHeader(onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primarySurface)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        val switcher = painterResource(id = R.drawable.ic_theme_switcher)
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                )
                .align(Alignment.End)
                .padding(8.dp)
                .size(32.dp, 30.dp)
        ) {
            Icon(
                painter = switcher,
                contentDescription = "Switch theme",
                tint = MaterialTheme.colors.primary
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
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = "hello1234@shiftweather.ru",
                style = MaterialTheme.typography.subtitle1,
                fontSize = 18.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }

    }

}