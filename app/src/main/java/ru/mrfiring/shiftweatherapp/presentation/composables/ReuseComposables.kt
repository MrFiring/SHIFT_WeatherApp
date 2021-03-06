package ru.mrfiring.shiftweatherapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R

@Composable
fun ShowAppBar(title: String) {
    if (MaterialTheme.colors.isLight) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondaryVariant
                )
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5,
                )
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun ShowLoading(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
        Text(
            text = "Loading...", style = MaterialTheme.typography.button,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ShowNetworkError(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        modifier = modifier
    ) {
        val errorImg = painterResource(id = R.drawable.ic_connection_error)
        val text = stringResource(id = R.string.network_error)
        Image(
            painter = errorImg,
            contentDescription = "",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )
        Text(
            text = text, style = MaterialTheme.typography.button,
            modifier = Modifier.padding(8.dp)
        )
        Button(onClick = {
            onRetry()
        }
        ) {
            Text(text = "Retry")
        }
    }


}