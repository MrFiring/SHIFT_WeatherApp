package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer

interface WeatherRepository {
    suspend fun getWeather(id: Long): DomainWeatherContainer
    suspend fun updateWeatherFromServer(id: Long)
    suspend fun getWeather(location: Location): DomainWeatherContainer
}