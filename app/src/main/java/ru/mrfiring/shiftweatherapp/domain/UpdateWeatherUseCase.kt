package ru.mrfiring.shiftweatherapp.domain

class UpdateWeatherUseCase(private val repository: WeatherRepository) {

    operator fun invoke(id: Long) = repository.updateWeatherFromServer(id)
}