package ru.mrfiring.shiftweatherapp.domain

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(id: Long): DomainWeatherContainer {
        return weatherRepository.getWeather(id)
    }
}