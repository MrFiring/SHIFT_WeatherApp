package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

interface CitiesRepository {
    @ExperimentalPagingApi
    fun getCitiesLiveData(): Flow<PagingData<DomainCity>>
}