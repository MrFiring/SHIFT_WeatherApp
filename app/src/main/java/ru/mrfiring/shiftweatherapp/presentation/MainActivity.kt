package ru.mrfiring.shiftweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import org.koin.android.ext.android.inject
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.home.HomeScreen
import ru.mrfiring.shiftweatherapp.presentation.splash.SplashScreen
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme


object Navigations {
    const val Splash = "splash"
    const val Home = "home"
    const val Details = "details"

}

class MainActivity : ComponentActivity() {
    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val themeState by inject<MutableState<Boolean>>()
            WeatherTheme(darkTheme = themeState.value) {
                Surface {
                    NavHost(navController = navController, startDestination = Navigations.Splash) {
                        composable(route = Navigations.Splash) {
                            SplashScreen(navController = navController)
                        }
                        composable(route = Navigations.Home) {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            route = "${Navigations.Details}/{cityId}",
                            arguments = listOf(navArgument("cityId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getLong("cityId")?.let {
                                DetailScreen(navController = navController, it)
                            }
                        }
                    }
                }

            }
        }

    }
}