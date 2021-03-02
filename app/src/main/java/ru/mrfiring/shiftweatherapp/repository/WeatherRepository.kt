package ru.mrfiring.shiftweatherapp.repository


import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.repository.database.*
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.repository.domain.asDomainObject
import ru.mrfiring.shiftweatherapp.repository.network.CitiesParser
import ru.mrfiring.shiftweatherapp.repository.network.City
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherApi
import ru.mrfiring.shiftweatherapp.repository.network.asDatabaseObject
import ru.mrfiring.shiftweatherapp.repository.paging.CityMediator

class WeatherRepository(context: Context) {
    private val database = getDatabase(context)

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
            remoteMediator = CityMediator(OpenWeatherApi.openWeatherService, database),

        ).liveData.map {
            it.map { item ->
                item.asDomainObject()
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

private lateinit var INSTANCE: WeatherRepository
fun getRepository(context: Context): WeatherRepository {
    synchronized(WeatherRepository::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = WeatherRepository(context)
        }
    }
    return INSTANCE
}