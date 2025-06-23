package ru.mrfiring.shiftweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.di.get
import ru.mrfiring.shiftweatherapp.domain.models.DomainDrawerMenuItem
import ru.mrfiring.shiftweatherapp.domain.onboarding.IsOnboardingPassedUseCase
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreen
import ru.mrfiring.shiftweatherapp.presentation.composables.ShowAppBar
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerContent
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerHeader
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesScreen
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreen
import ru.mrfiring.shiftweatherapp.presentation.onboarding.OnboardingScreen
import ru.mrfiring.shiftweatherapp.presentation.theme.WeatherTheme


object Navigations {
    const val Onboarding = "onboarding"
    const val Cities = "cities"
    const val Favorites = "favorites"
    const val Details = "details"
    const val Countries = "countries"
}

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val isOnboardingPassedUseCase: IsOnboardingPassedUseCase by inject()

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationGranted =
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val defaultContext = defaultComponentContext()

        setContent {
            val navController = rememberNavController()
            val themeState = remember { mutableStateOf(true) }
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            WeatherTheme(darkTheme = themeState.value) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        ShowAppBar(
                            title = "Weather",
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
                                    onClick = {
                                        navController.navigate(Navigations.Countries) {
                                            launchSingleTop = true
                                        }
                                    }),
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
                    it.calculateBottomPadding()
                    MainNavigationGraph(
                        navController = navController,
                        startDestination = getStartDestination(
                            isOnboardingPassedUseCase(),
                            locationGranted
                        ),
                        defaultComponentContext = defaultContext
                    )
                }

            }
        }

    }
}

private fun getStartDestination(onboardingPassed: Boolean, locationGranted: Boolean) =
    when {
        !onboardingPassed -> Navigations.Onboarding
        !locationGranted -> Navigations.Cities
        else -> Navigations.Details
    }

@ExperimentalFoundationApi
@ExperimentalPagingApi
@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    defaultComponentContext: ComponentContext,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Navigations.Onboarding) {
            OnboardingScreen(
                onLocationAvailable = { navController.navigate(Navigations.Details) },
                onNext = { navController.navigate(Navigations.Cities) },
            )
        }

        composable(route = Navigations.Favorites) {
            FavoriteScreen(navController = navController)
        }

        composable(route = Navigations.Cities) {

            val component = CitiesComponentImpl(
                componentContext = defaultComponentContext,
                country = "",
                onCitySelect = { city ->
                    navController.navigate("${Navigations.Details}?cityId=${city.id}") {
                        //Need to navigateUp correctly on a real device
                        popUpTo(Navigations.Cities) { inclusive = false }
                    }
                },
                getCitiesFlowUseCase = get(),
                getCitiesByCountryFlowUseCase = get(),
                updateCityUseCase = get(),
            )

            CitiesScreen(component)
        }

        composable(
            route = "${Navigations.Cities}/?country={country}",
            arguments = listOf(navArgument("country") { defaultValue = "" })
        ) { backStackEntry ->
            val arg = backStackEntry.arguments?.getString("country")

            val component = CitiesComponentImpl(
                componentContext = defaultComponentContext,
                country = arg ?: "",
                onCitySelect = { city ->
                    navController.navigate("${Navigations.Details}?cityId=${city.id}") {
                        //Need to navigateUp correctly on a real device
                        popUpTo(Navigations.Cities) { inclusive = false }
                    }
                },
                getCitiesFlowUseCase = get(),
                getCitiesByCountryFlowUseCase = get(),
                updateCityUseCase = get(),
            )

            CitiesScreen(component)
        }

        composable(route = Navigations.Countries) {
            CountriesScreen(navController = navController)
        }

        composable(
            route = "${Navigations.Details}?cityId={cityId}",
            arguments = listOf(navArgument("cityId") {
                type = NavType.LongType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val cityId = backStackEntry.arguments?.getLong("cityId")?.run {
                if (this != -1L) {
                    this
                } else {
                    null
                }
            }

            val component = DetailComponentImpl(
                componentContext = defaultComponentContext,
                cityId = cityId,
                updateWeatherUseCase = get(),
                getWeatherUseCase = get(),
                getCurrentLocationUseCase = get(),
            )

            DetailScreen(component)
        }
    }
}
