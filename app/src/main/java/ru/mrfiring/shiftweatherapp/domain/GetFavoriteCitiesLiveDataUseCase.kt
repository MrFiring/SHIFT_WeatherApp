package ru.mrfiring.shiftweatherapp.domain

import androidx.lifecycle.LiveData
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

class GetFavoriteCitiesLiveDataUseCase(private val citiesRepository: CitiesRepository) {

    suspend operator fun invoke(): LiveData<List<DomainCity>> = citiesRepository.getFavoriteCities()

}