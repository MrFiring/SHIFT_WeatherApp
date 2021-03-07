package ru.mrfiring.shiftweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import org.koin.android.ext.android.inject
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowAppBar
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerContent
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerHeader
import ru.mrfiring.shiftweatherapp.presentation.composables.home.items
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
            val scaffoldState = rememberScaffoldState()
            WeatherTheme(darkTheme = themeState.value) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = { ShowAppBar(title = "Weather") },
                    drawerContent = {
                        HomeDrawerHeader { themeState.value = !themeState.value }
                        Divider(modifier = Modifier.padding(bottom = 8.dp))
                        HomeDrawerContent(items)
                    }
                ) {
                    MainNavigationGraph(navController = navController)
                }

            }
        }

    }
}

@ExperimentalPagingApi
@Composable
fun MainNavigationGraph(navController: NavHostController) {
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
