package ru.mrfiring.shiftweatherapp.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.mrfiring.shiftweatherapp.repository.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.repository.database.RemoteKey
import ru.mrfiring.shiftweatherapp.repository.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.repository.network.CitiesParser
import ru.mrfiring.shiftweatherapp.repository.network.City
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.repository.network.asDatabaseObject
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class CityMediator(
    private val weatherService: OpenWeatherService,
    val database: WeatherDatabase
) : RemoteMediator<Int, DatabaseCity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DatabaseCity>
    ): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> {
                //Check if there is data in db
                val citiesCount = database.citiesDao.getCountOfCities()
                if(citiesCount > 0){
                  return MediatorResult.Success(endOfPaginationReached = true)
                }

                return try {
                    val citiesList: List<City> = loadCities()
                    database.withTransaction {
                        database.citiesDao.insertCities(citiesList.map {
                            it.asDatabaseObject()
                        })
                    }
                    MediatorResult.Success(endOfPaginationReached = false)


                } catch (ex: IOException) {
                    MediatorResult.Error(ex)
                } catch (ex: HttpException) {
                    MediatorResult.Error(ex)
                }
            }
            else -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private suspend fun loadCities(): List<City> {
        val response = weatherService.getCitiesFile()
        val parser = CitiesParser()
        val decodedString = parser.decompressGZip(response)
        return parser.parseJson(decodedString)
    }
}