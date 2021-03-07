package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi

class GetCitiesFlowUseCase(private val repository: CitiesRepository) {
    @ExperimentalPagingApi
    operator fun invoke() = repository.getCitiesFlow()
}