package ru.mrfiring.shiftweatherapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.models.City

class CitiesRepositoryImpl @ExperimentalPagingApi constructor(
    private val citiesDao: CitiesDao,
    private val cityMediator: CityMediator,
) : CitiesRepository {

    @ExperimentalPagingApi
    override fun getCitiesFlow(): Flow<PagingData<City>> {
        val pagingSourceFactory = {
            citiesDao.getCities()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                //If maxSize isn't set it will lead to OutOfMemory.
                //Due to by default maxSize has value of infinity it means that page never will be dropped
                maxSize = 130 //maxSize = pageSize + 2*preFetchSize + 10 (from me)
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = cityMediator,

            ).flow.map {
            it.map { item ->
                item.asDomainObject()
            }
        }
    }

    override fun getCitiesByCountryFlow(country: String): Flow<PagingData<City>> {
        val pagingSourceFactory = {
            citiesDao.getCitiesByCountry(country)
        }
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                maxSize = 130
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { item ->
                item.asDomainObject()
            }
        }
    }

    override suspend fun getFavoriteCities(): List<City> =
        citiesDao.getFavoriteCities().map { city ->
            city.asDomainObject()
        }

    override suspend fun updateCity(city: City) {
        citiesDao.updateCity(city.asDatabaseObject())
    }
}