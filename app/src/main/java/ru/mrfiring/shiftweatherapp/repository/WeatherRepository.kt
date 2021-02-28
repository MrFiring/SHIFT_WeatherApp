package ru.mrfiring.shiftweatherapp.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.repository.database.*
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.domain.DomainWeather
import ru.mrfiring.shiftweatherapp.repository.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.repository.domain.asDomainObject
import ru.mrfiring.shiftweatherapp.repository.network.CitiesParser
import ru.mrfiring.shiftweatherapp.repository.network.City
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherApi
import ru.mrfiring.shiftweatherapp.repository.network.asDatabaseObject
import java.io.IOException

class WeatherRepository(context: Context) {
    private val database = getDatabase(context)

    suspend fun getWeather(id: Long): DomainWeatherContainer?{
         val container: DatabaseWeatherContainer? = database.weatherDao.getWeatherContainerById(id)

         container?.let {
             val weatherParams = database.weatherDao.getMainWeatherParametersById(id)
             val weatherList = database.weatherDao.getWeatherListById(id)

             val weather: DatabaseWeather = if(!weatherList.isNullOrEmpty()){
                 weatherList[0]
             }else{
                 DatabaseWeather(id, -1, "", "", "")
             }

             return container.asDomainObject(
                     weather,
                     weatherParams,
                     DatabaseWind(id, -1.0, -1.0, -1.0),
                     null,
                     null
             )
         }

        return null
    }

    suspend fun getCities(): List<DomainCity>{
        val dbList = database.citiesDao.getCities()
        return if(dbList.isNullOrEmpty()){
            loadCitiesFromServer()
            getCities()
        }else{
            dbList.map {
                it.asDomainObject()
            }
        }
    }

    private suspend fun loadCitiesFromServer(){
        withContext(Dispatchers.IO){
            val responseBody = OpenWeatherApi.openWeatherService.getCitiesFile()
            val parser = CitiesParser()
            val rawData = parser.decompressGZip(responseBody)
            val list: List<City> = parser.parseJson(rawData)

            database.citiesDao.insertCities(list.map {
                it.asDatabaseObject()
            })
        }
    }

    suspend fun updateWeatherFromServer(id: Long){
        withContext(Dispatchers.IO){
            val dbCity = database.citiesDao.getCityById(id)
            val container = OpenWeatherApi.openWeatherService.getWeatherContainerByCoordinates(
                dbCity.latitude, dbCity.longitude
            )

            database.weatherDao.insertMainWeatherParameters(container.main.asDatabaseObject(id))
            database.weatherDao.insertWeatherContainer(container.asDatabaseObject(container.clouds.all))
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

private lateinit var INSTANCE: WeatherRepository
fun getRepository(context: Context): WeatherRepository {
    synchronized(WeatherRepository::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = WeatherRepository(context)
        }
    }
    return INSTANCE
}