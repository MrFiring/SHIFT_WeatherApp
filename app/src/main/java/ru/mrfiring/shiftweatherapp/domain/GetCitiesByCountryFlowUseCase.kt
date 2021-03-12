package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

class GetCitiesByCountryFlowUseCase(private val citiesRepository: CitiesRepository) {
    operator fun invoke(country: String): Flow<PagingData<DomainCity>> {
        return citiesRepository.getCitiesByCountryFlow(country)
    }
}