package ru.mrfiring.shiftweatherapp.domain

import io.reactivex.Single

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    operator fun invoke(id: Long): Single<DomainWeatherContainer> {
        return weatherRepository.getWeather(id)
    }
}