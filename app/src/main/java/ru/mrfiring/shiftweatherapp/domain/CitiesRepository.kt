package ru.mrfiring.shiftweatherapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

interface CitiesRepository {
    @ExperimentalPagingApi
    fun getCitiesFlow(): Flow<PagingData<DomainCity>>

    fun getCitiesByCountryFlow(country: String): Flow<PagingData<DomainCity>>

    suspend fun getFavoriteCities(): LiveData<List<DomainCity>>
    suspend fun updateCity(city: DomainCity)

}