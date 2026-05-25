package ru.mrfiring.shiftweatherapp.domain.models

import kotlin.math.roundToInt


data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    var favorite: Boolean
)

data class WeatherContainer(
    val lastUpdate: Long,
    val timeZone: Long,
    val clouds: Double,
    val weather: Weather,
    val mainParams: MainWeatherParameters,
    val wind: Wind,
    val rain: Rain?,
    val snow: Snow?,

    )

data class Weather(
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainWeatherParameters(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Double,
    val pressureAtSeaLevel: Int,
    val pressureAtGroundLevel: Int
)

data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double
) {
    //The variable deg show the direction of wind (where it comes from),
    //So that we start to count from North it's zero deg
    fun degToWord(): String {
        when (deg.roundToInt()) {
            0 -> {
                return "N"
            }
            90 -> {
                return "E"
            }
            180 -> {
                return "S"
            }
            270 -> {
                return "W"
            }
            in 1..89 -> {
                return "NE"
            }
            in 91..179 -> {
                return "SE"
            }
            in 181..269 -> {
                return "SW"
            }
            in 271..359 -> {
                return "NW"
            }
            else -> {
                return "NA"
            }
        }

    }
}

data class Rain(
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)


data class Snow(
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)


