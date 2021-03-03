package ru.mrfiring.shiftweatherapp.data

import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.data.database.*
import ru.mrfiring.shiftweatherapp.data.network.*
import ru.mrfiring.shiftweatherapp.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.domain.WeatherRepository
import ru.mrfiring.shiftweatherapp.domain.asDomainObject

class WeatherRepositoryImpl @ExperimentalPagingApi constructor(
        private val weatherDao: WeatherDao,
        private val citiesDao: CitiesDao,
        private val networkService: OpenWeatherService,
): WeatherRepository {
    override suspend fun getWeather(id: Long): DomainWeatherContainer{
         val container: DatabaseWeatherContainer = weatherDao.getWeatherContainerById(id)

        return container.asDomainObject()
    }
    override suspend fun updateWeatherFromServer(id: Long){
        withContext(Dispatchers.IO){
            val dbCity = citiesDao.getCityById(id)
            val container = networkService.getWeatherContainerByCoordinates(
                dbCity.latitude, dbCity.longitude
            )

            weatherDao.insertMainWeatherParameters(container.main.asDatabaseObject(id))
            weatherDao.insertWeatherContainer(container.asDatabaseObject())
            weatherDao.insertWind(container.wind.asDatabaseObject(id))
            val dbWeather = mutableListOf<DatabaseWeather>()

            for (weather in container.weather){
                dbWeather.add(weather.asDatabaseObject(id))
            }
            weatherDao.insertAllWeather(dbWeather)
            container.rain?.let {
                weatherDao.insertRain(it.asDatabaseObject(id))
            }
            container.snow?.let {
                weatherDao.insertSnow(it.asDatabaseObject(id))
            }


        }
    }
}
