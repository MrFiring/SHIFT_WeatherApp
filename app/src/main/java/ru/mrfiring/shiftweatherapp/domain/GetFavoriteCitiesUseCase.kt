package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.models.City

class GetFavoriteCitiesUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke(): List<City> = citiesRepository.getFavoriteCities()
}