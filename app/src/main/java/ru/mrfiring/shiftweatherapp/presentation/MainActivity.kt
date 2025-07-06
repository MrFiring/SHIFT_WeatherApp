package ru.mrfiring.shiftweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.defaultComponentContext
import org.koin.android.ext.android.get
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val themeState: MutableState<Boolean> = mutableStateOf(true)

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationGranted =
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val rootComponent = RootComponent(
            defaultComponentContext(),
            locationGranted,
            themeChangeHandler = {
                themeState.value = !themeState.value
            },
            isOnboardingPassedUseCase = get(),
            componentFactory = get()
        )

        setContent {
            WeatherTheme(darkTheme = themeState.value) {
                RootContent(rootComponent)
            }
        }
    }
}
