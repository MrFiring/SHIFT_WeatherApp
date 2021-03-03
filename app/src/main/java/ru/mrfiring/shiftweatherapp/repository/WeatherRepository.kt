package ru.mrfiring.shiftweatherapp.repository

import androidx.lifecycle.*
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.repository.database.*
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.repository.domain.asDomainObject
import ru.mrfiring.shiftweatherapp.repository.network.*
import ru.mrfiring.shiftweatherapp.repository.paging.CityMediator

class WeatherRepository @ExperimentalPagingApi constructor(
        private val database: WeatherDatabase,
        private val networkService: OpenWeatherService,
        private val cityMediator: CityMediator
) {
    suspend fun getWeather(id: Long): DomainWeatherContainer?{
         val container: DatabaseWeatherContainer? = database.weatherDao.getWeatherContainerById(id)

         container?.let {
             return container.asDomainObject()
         }

        return null
    }

    @ExperimentalPagingApi
    fun getCitiesLiveData(): LiveData<PagingData<DomainCity>>{
        val pagingSourceFactory = {
            database.citiesDao.getCities()
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

    suspend fun updateWeatherFromServer(id: Long){
        withContext(Dispatchers.IO){
            val dbCity = database.citiesDao.getCityById(id)
            val container = networkService.getWeatherContainerByCoordinates(
                dbCity.latitude, dbCity.longitude
            )

            database.weatherDao.insertMainWeatherParameters(container.main.asDatabaseObject(id))
            database.weatherDao.insertWeatherContainer(container.asDatabaseObject())
            database.weatherDao.insertWind(container.wind.asDatabaseObject(id))
            val dbWeather = mutableListOf<DatabaseWeather>()

            for (weather in container.weather){
                dbWeather.add(weather.asDatabaseObject(id))
            }
            database.weatherDao.insertAllWeather(dbWeather)
            container.rain?.let {
                database.weatherDao.insertRain(it.asDatabaseObject(id))
            }
            container.snow?.let {
                database.weatherDao.insertSnow(it.asDatabaseObject(id))
            }


        }
    }

}
