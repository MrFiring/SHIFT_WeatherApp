package ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
        Row(
            modifier = if (MaterialTheme.colors.isLight) {
                Modifier
            } else {
                Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary
                )
            }
        ) {
            Image(
                painter = painter,
                contentDescription = "Menu Item",
                modifier = Modifier
                    .padding(8.dp)
                    .width(24.dp)
                    .height(24.dp),
                colorFilter = if (MaterialTheme.colors.isLight) {
                    ColorFilter.tint(MaterialTheme.colors.primary)
                } else {
                    null
                }

            )
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(24.dp, 8.dp, 0.dp, 8.dp)

            )
        }
    }

}