package ru.mrfiring.shiftweatherapp.presentation.composables.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.data.network.FLAG_URL
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
            val url = FLAG_URL.format(domainCity.country)
            GlideImage(
                data = url,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp, 64.dp)
                    .padding(8.dp, 8.dp, 4.dp, 8.dp),
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.ic_connection_error),
                        contentDescription = null
                    )
                }
            )
            Text(text = domainCity.name, style = MaterialTheme.typography.h5)
            Text(
                text = domainCity.country, style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}