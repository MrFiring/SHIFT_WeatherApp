package ru.mrfiring.shiftweatherapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.*
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.network.City
import ru.mrfiring.shiftweatherapp.data.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.DomainCity
import ru.mrfiring.shiftweatherapp.domain.asDomainObject

class CitiesRepositoryImpl @ExperimentalPagingApi constructor(
    private val citiesDao: CitiesDao,
    private val cityMediator: CityMediator,
    private val weatherService: OpenWeatherService,
    private val citiesParser: CitiesParser
): CitiesRepository {

    @ExperimentalPagingApi
    override fun getCitiesLiveData(): LiveData<PagingData<DomainCity>> {
        val pagingSourceFactory = {
            citiesDao.getCities()
        }
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = cityMediator,

            ).liveData.map {
            it.map { item ->
                item.asDomainObject()
            }
        }
    }

    override suspend fun loadCities(): List<City> {
        val response = weatherService.getCitiesFile()
        val decodedString = citiesParser.decompressGZip(response)
        return citiesParser.parseJson(decodedString)
    }
}