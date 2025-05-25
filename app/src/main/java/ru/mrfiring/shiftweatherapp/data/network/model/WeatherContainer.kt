package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherContainer(
    val coord: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: MainWeatherParameters,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain?,
    val snow: Snow?,
    val dt: Long,
    val sys: SystemInfo,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Int
)