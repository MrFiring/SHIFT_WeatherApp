package ru.mrfiring.shiftweatherapp.data

import androidx.paging.ExperimentalPagingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.WeatherDao
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeather
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeatherContainer
import ru.mrfiring.shiftweatherapp.data.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.domain.WeatherRepository
import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer

class WeatherRepositoryImpl @ExperimentalPagingApi constructor(
        private val weatherDao: WeatherDao,
        private val citiesDao: CitiesDao,
        private val networkService: OpenWeatherService,
): WeatherRepository {
    override suspend fun getWeather(id: Long): WeatherContainer{
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

    override suspend fun getWeather(location: Location): WeatherContainer {
        val model =
            networkService.getWeatherContainerByCoordinates(location.latitude, location.longitude)

        return model.asDatabaseObject().asDomainObject()
    }
}
