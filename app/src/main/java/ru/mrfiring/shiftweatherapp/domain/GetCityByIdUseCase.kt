package ru.mrfiring.shiftweatherapp.domain

import ru.mrfiring.shiftweatherapp.domain.models.City

class GetCityByIdUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke(id: Long): City = citiesRepository.getCityById(id)
}
