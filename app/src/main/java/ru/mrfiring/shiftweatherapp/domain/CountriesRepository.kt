package ru.mrfiring.shiftweatherapp.domain

interface CountriesRepository {

    suspend fun getCountries(): List<String>

}