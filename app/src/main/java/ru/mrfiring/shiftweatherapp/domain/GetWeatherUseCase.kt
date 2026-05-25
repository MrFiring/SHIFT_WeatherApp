package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(id: Long): WeatherContainer {
        return weatherRepository.getWeather(id)
    }

    suspend operator fun invoke(location: Location): WeatherContainer =
        weatherRepository.getWeather(location)
}