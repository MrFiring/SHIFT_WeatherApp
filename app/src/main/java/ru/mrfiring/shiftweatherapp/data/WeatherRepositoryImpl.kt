package ru.mrfiring.shiftweatherapp.data

import androidx.paging.ExperimentalPagingApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.DatabaseWeather
import ru.mrfiring.shiftweatherapp.data.database.WeatherDao
import ru.mrfiring.shiftweatherapp.data.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.data.network.asDatabaseObject
import ru.mrfiring.shiftweatherapp.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.domain.WeatherRepository
import ru.mrfiring.shiftweatherapp.domain.asDomainObject

class WeatherRepositoryImpl @ExperimentalPagingApi constructor(
    private val weatherDao: WeatherDao,
    private val citiesDao: CitiesDao,
    private val networkService: OpenWeatherService,
) : WeatherRepository {
    override fun getWeather(id: Long): Single<DomainWeatherContainer> {
        return weatherDao.getWeatherContainerById(id).map {
            it.asDomainObject()
        }
    }

    override fun updateWeatherFromServer(id: Long): Completable {
        return citiesDao.getCityById(id)
            .subscribeOn(Schedulers.io())
            .flatMap { dbCity ->
                networkService.getWeatherContainerByCoordinates(
                    dbCity.latitude,
                    dbCity.longitude
                )
            }
            .flatMapCompletable { container ->
                val dbWeather = mutableListOf<DatabaseWeather>()
                for (weather in container.weather) {
                    dbWeather.add(weather.asDatabaseObject(id))
                }
                Completable.merge (
                    weatherDao.insertMainWeatherParameters(container.main.asDatabaseObject(id))
                    weatherDao.insertWeatherContainer(container.asDatabaseObject())
                    weatherDao.insertWind(container.wind.asDatabaseObject(id))
                    weatherDao.insertAllWeather(dbWeather)
                )
            }
    }
}
