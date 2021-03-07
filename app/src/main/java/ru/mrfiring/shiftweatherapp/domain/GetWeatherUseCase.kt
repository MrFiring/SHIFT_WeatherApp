package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(id: Long): DomainWeatherContainer {
        return weatherRepository.getWeather(id)
    }
}