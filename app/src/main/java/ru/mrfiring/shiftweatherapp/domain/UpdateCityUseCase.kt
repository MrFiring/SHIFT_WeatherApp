package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

class UpdateCityUseCase(private val citiesRepository: CitiesRepository) {
    suspend operator fun invoke(city: DomainCity) {
        citiesRepository.updateCity(city)
    }
}