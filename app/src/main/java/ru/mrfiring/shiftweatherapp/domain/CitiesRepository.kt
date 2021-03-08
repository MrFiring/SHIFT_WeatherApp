package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import io.reactivex.Flowable

interface CitiesRepository {
    @ExperimentalPagingApi
    fun getCitiesFlowable(): Flowable<PagingData<DomainCity>>
}