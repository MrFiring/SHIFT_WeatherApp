package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer

interface WeatherRepository {
    suspend fun getWeather(id: Long): WeatherContainer
    suspend fun updateWeatherFromServer(id: Long)
    suspend fun getWeather(location: Location): WeatherContainer
}