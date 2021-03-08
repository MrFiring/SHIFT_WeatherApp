package ru.mrfiring.shiftweatherapp.domain

import io.reactivex.Maybe
import io.reactivex.disposables.Disposable

interface WeatherRepository {
    fun getWeather(id: Long): Maybe<DomainWeatherContainer>
    fun updateWeatherFromServer(id: Long): Disposable
}