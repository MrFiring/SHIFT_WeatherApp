package ru.mrfiring.shiftweatherapp.domain

class UpdateWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(id: Long) = repository.updateWeatherFromServer(id)
}