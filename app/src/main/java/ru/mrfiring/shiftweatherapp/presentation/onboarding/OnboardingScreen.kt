package ru.mrfiring.shiftweatherapp.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.theme.Orange800
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme

@Composable
fun OnboardingScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Welcome to SunWeather app!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(R.drawable.ic_logo_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Black, CircleShape)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "To show you the most accurate weather for your current location, SunWeather needs access to your device's location. We only use this to fetch weather data for where you are right now",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Tap the button below to allow access",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSecondary
                )
            }

            // TODO Remove hardcoded colors. There is a problem with theme in all over the project.
            Column {
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text("Allow Location Access", color = Orange800)
                }

                TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text("Go next without permission", color = Orange800)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OnboardingDarkPreview() {
    WeatherTheme(true) {
        OnboardingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    WeatherTheme(false) {
        OnboardingScreen()
    }
}