package ru.mrfiring.shiftweatherapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.presentation.Navigations

@Composable
fun SplashScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val logo = painterResource(id = R.drawable.ic_logo_foreground)
        Image(
            painter = logo,
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .background(
                    color = Color.Black,
                    shape = CircleShape
                )
        )

        navController.navigate(Navigations.Cities) {
            popUpTo(Navigations.Splash) {
                inclusive = true
            }
        }

    }

}
