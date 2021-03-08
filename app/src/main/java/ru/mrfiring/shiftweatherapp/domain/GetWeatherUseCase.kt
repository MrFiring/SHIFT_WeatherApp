package ru.mrfiring.shiftweatherapp.domain

import io.reactivex.Maybe

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    operator fun invoke(id: Long): Maybe<DomainWeatherContainer> {
        return weatherRepository.getWeather(id)
    }
}