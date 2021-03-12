package ru.mrfiring.shiftweatherapp.data

import androidx.lifecycle.LiveData
import ru.mrfiring.shiftweatherapp.data.database.CountriesDao
import ru.mrfiring.shiftweatherapp.domain.CountriesRepository

class CountriesRepositoryImpl(
    private val countriesDao: CountriesDao
) : CountriesRepository {

    override fun getCountriesLiveData(): LiveData<List<String>> =
        countriesDao.getCountriesLiveData()
}