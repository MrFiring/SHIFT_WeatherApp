package ru.mrfiring.shiftweatherapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth

@Composable
fun DrawerMenuItem(painter: Painter, text: String, onClick: () -> Unit) {
    CardWithPaddingAndFillWidth(
        modifier = Modifier
            .clickable {
                onClick()
            },
        padding = PaddingValues(8.dp, 4.dp, 8.dp, 4.dp)
    ) {
        Row {
            Image(
                painter = painter,
                contentDescription = "Menu Item",
                modifier = Modifier
                    .padding(8.dp)
                    .width(24.dp)
                    .height(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(24.dp, 8.dp, 0.dp, 8.dp)

            )
        }
    }

}