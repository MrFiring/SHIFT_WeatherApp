package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.data.database.CountriesDao
import ru.mrfiring.shiftweatherapp.domain.CountriesRepository

class CountriesRepositoryImpl(
    private val countriesDao: CountriesDao
) : CountriesRepository {

    override suspend fun getCountries(): List<String> =
        countriesDao.getCountries()
}