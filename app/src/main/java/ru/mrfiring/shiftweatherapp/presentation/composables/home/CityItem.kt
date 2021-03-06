package ru.mrfiring.shiftweatherapp.presentation.composables.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.data.network.FLAG_URL
import ru.mrfiring.shiftweatherapp.domain.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.theme.ThemeAwareCard

@Composable
fun CityItem(domainCity: DomainCity, onClick: () -> Unit) {
    ThemeAwareCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
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