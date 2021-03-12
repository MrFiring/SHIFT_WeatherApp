package ru.mrfiring.shiftweatherapp.domain

import androidx.lifecycle.LiveData

interface CountriesRepository {

    fun getCountriesLiveData(): LiveData<List<String>>

}