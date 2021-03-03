package ru.mrfiring.shiftweatherapp.domain

interface WeatherRepository {
    suspend fun getWeather(id: Long): DomainWeatherContainer?
    suspend fun updateWeatherFromServer(id: Long)
}