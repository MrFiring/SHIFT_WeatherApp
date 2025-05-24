package ru.mrfiring.shiftweatherapp.presentation.composables.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth

@Composable
fun CityItem(domainCity: DomainCity, onLongTap: (Offset) -> Unit = {}, onClick: (Offset) -> Unit) {
    CardWithPaddingAndFillWidth(
        modifier = Modifier
            .pointerInput(Unit) { //To determine longPress and tap.
                detectTapGestures(
                    onLongPress = onLongTap,
                    onTap = onClick
                )
            },
        padding = PaddingValues(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = domainCity.name, style = MaterialTheme.typography.h5)
            Text(
                text = domainCity.country, style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}