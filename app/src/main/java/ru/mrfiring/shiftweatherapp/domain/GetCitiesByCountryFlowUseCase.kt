package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mrfiring.shiftweatherapp.domain.models.City

class GetCitiesByCountryFlowUseCase(private val citiesRepository: CitiesRepository) {
    operator fun invoke(country: String): Flow<PagingData<City>> {
        return citiesRepository.getCitiesByCountryFlow(country)
    }
}