package ru.mrfiring.shiftweatherapp.domain.models

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