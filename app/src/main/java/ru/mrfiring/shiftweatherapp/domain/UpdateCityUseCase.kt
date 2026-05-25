package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.models.City

class UpdateCityUseCase(private val citiesRepository: CitiesRepository) {
    suspend operator fun invoke(city: City) {
        citiesRepository.updateCity(city)
    }
}