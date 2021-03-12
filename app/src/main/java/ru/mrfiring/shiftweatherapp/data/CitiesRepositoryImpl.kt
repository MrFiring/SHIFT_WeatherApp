package ru.mrfiring.shiftweatherapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

class CitiesRepositoryImpl @ExperimentalPagingApi constructor(
    private val citiesDao: CitiesDao,
    private val cityMediator: CityMediator,
): CitiesRepository {

    @ExperimentalPagingApi
    override fun getCitiesFlow(): Flow<PagingData<DomainCity>> {
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

    override fun getCitiesByCountryFlow(country: String): Flow<PagingData<DomainCity>> {
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

    override suspend fun getFavoriteCities(): LiveData<List<DomainCity>> {
        return Transformations.map(citiesDao.getFavoriteCities()) {
            it.map { city ->
                city.asDomainObject()
            }
        }
    }

    override suspend fun updateCity(city: DomainCity) {
        citiesDao.updateCity(city.asDatabaseObject())
    }
}