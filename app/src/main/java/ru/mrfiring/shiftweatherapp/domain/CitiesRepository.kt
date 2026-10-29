package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mrfiring.shiftweatherapp.domain.models.City

interface CitiesRepository {
    @ExperimentalPagingApi
    fun getCitiesFlow(): Flow<PagingData<City>>

    fun getCitiesByCountryFlow(country: String): Flow<PagingData<City>>

    suspend fun getFavoriteCities(): List<City>

    suspend fun getCityById(id: Long): City

    suspend fun updateCity(city: City)

}