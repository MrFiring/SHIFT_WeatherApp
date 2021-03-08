package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi

class GetCitiesFlowableUseCase(private val repository: CitiesRepository) {
    @ExperimentalPagingApi
    operator fun invoke() = repository.getCitiesFlowable()
}