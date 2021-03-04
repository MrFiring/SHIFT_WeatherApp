package ru.mrfiring.shiftweatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R

@Composable
fun ShowLoading() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
        Text(
            text = "Loading...", style = MaterialTheme.typography.button,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ShowNetworkError(onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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