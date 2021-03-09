package ru.mrfiring.shiftweatherapp.domain

import io.reactivex.Completable
import io.reactivex.Single

interface WeatherRepository {
    fun getWeather(id: Long): Single<DomainWeatherContainer>
    fun updateWeatherFromServer(id: Long): Completable
}