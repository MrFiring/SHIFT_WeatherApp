package ru.mrfiring.shiftweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.koin.android.ext.android.get
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val locationGranted =
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val rootComponent = RootComponent(
            defaultComponentContext(),
            locationGranted,
            isOnboardingPassedUseCase = get(),
            getDarkThemeUseCase = get(),
            setDarkThemeUseCase = get(),
            componentFactory = get()
        )

        setContent {
            val darkTheme by rootComponent.darkThemeState.subscribeAsState()

            WeatherTheme(darkTheme = darkTheme) {
                RootContent(rootComponent, modifier = Modifier.systemBarsPadding())
            }
        }
    }
}
