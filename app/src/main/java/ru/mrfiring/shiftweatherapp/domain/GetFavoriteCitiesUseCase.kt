package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

class GetFavoriteCitiesUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke(): List<DomainCity> = citiesRepository.getFavoriteCities()

}