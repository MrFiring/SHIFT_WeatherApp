package ru.mrfiring.shiftweatherapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailScreen
import ru.mrfiring.shiftweatherapp.presentation.home.HomeScreen


object Navigations {
    const val Home = "home"
    const val Details = "details"

}

class MainActivity : AppCompatActivity() {
    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(navController = navController, startDestination = Navigations.Home) {
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