package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

@Composable
fun HomeDrawerHeader(onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.extendedColors.surfaceContainer)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        val switcher = painterResource(id = R.drawable.ic_theme_switcher)
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = CircleShape
                )
                .align(Alignment.End)
                .padding(8.dp)
                .size(32.dp, 30.dp)
        ) {
            Icon(
                painter = switcher,
                contentDescription = "Switch theme",
                tint = MaterialTheme.colorScheme.primary
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
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "hello1234@shiftweather.ru",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }

}