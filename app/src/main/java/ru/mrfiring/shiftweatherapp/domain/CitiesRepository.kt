package ru.mrfiring.shiftweatherapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.*
import ru.mrfiring.shiftweatherapp.data.network.City

interface CitiesRepository {
    @ExperimentalPagingApi
    fun getCitiesLiveData(): LiveData<PagingData<DomainCity>>
}