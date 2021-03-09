package ru.mrfiring.shiftweatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.paging.ExperimentalPagingApi
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.DomainDrawerMenuItem
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowAppBar
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerContent
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerHeader
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreen
import ru.mrfiring.shiftweatherapp.presentation.splash.SplashScreen
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme


object Navigations {
    const val Splash = "splash"
    const val Cities = "cities"
    const val Favorites = "favorites"
    const val Details = "details"

}

class MainActivity : ComponentActivity() {
    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val themeState = remember { mutableStateOf(true) }
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            WeatherTheme(darkTheme = themeState.value) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        ShowAppBar(title = "Weather",
                            onNavigationClick = {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        )
                    },
                    drawerContent = {
                        HomeDrawerHeader { themeState.value = !themeState.value }
                        Divider(modifier = Modifier.padding(bottom = 8.dp))
                        HomeDrawerContent(
                            listOf(
                                DomainDrawerMenuItem(
                                    drawableId = R.drawable.ic_city_icon,
                                    text = "Show cities",
                                    onClick = {
                                        navController.navigate(Navigations.Cities) {
                                            launchSingleTop = true
                                        }
                                    }),
                                DomainDrawerMenuItem(
                                    drawableId = R.drawable.ic_country_icon,
                                    text = "Show countries",
                                    onClick = { /*TODO*/ }),
                                DomainDrawerMenuItem(
                                    drawableId = R.drawable.ic_favorite,
                                    text = "Favorites",
                                    onClick = {
                                        navController.navigate(Navigations.Favorites) {
                                            launchSingleTop = true
                                            popUpTo(Navigations.Cities) {}
                                        }
                                    }),
                                DomainDrawerMenuItem(
                                    drawableId = R.drawable.ic_settings,
                                    text = "Settings",
                                    onClick = { /*TODO*/ })
                            )
                        )
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

        composable(route = Navigations.Cities) {
            CitiesScreen(navController = navController)
        }

        composable(route = Navigations.Favorites) {
            FavoriteScreen(navController = navController)
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
