package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi

class GetCitiesLiveDataUseCase(private val repository: CitiesRepository) {
    @ExperimentalPagingApi
    operator fun invoke() = repository.getCitiesLiveData()
}