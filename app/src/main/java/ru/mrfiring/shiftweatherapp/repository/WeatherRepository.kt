package ru.mrfiring.shiftweatherapp.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.repository.database.DatabaseWeather
import ru.mrfiring.shiftweatherapp.repository.database.DatabaseWeatherContainer
import ru.mrfiring.shiftweatherapp.repository.database.getDatabase
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
    val cities: LiveData<List<DomainCity>> = Transformations.map(
        database.citiesDao.getCities()
    ){ dbList ->
        dbList.map {
            it.asDomainObject()
        }
    }

     suspend fun getWeather(id: Long): LiveData<DomainWeatherContainer>{
         val liveData = MutableLiveData<DomainWeatherContainer>()

         val weatherParams = database.weatherDao.getMainWeatherParametersById(id)
         val weatherList = database.weatherDao.getWeatherListById(id)
         val weather: DatabaseWeather
         weather = if(weatherList.isNotEmpty())
             weatherList[0]
         else
             DatabaseWeather(id, -1, "", "", "")
         val wind = database.weatherDao.getWindById(id)
         val rain = database.weatherDao.getRainById(id)
         val snow = database.weatherDao.getSnowById(id)
         val container: DatabaseWeatherContainer? = database.weatherDao.getWeatherContainerById(id)

         container?.let {
             liveData.value = container.asDomainObject(
                weather,
                weatherParams,
                wind,
                rain,
                snow
            )
         }

        return liveData
    }

    suspend fun loadCitiesFromServer(){
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