package ru.mrfiring.shiftweatherapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.mrfiring.shiftweatherapp.data.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.data.network.*
import java.io.IOException

@ExperimentalPagingApi
class CityMediator(
    private val weatherService: OpenWeatherService,
    val database: WeatherDatabase,
    private val citiesParser: CitiesParser
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
                    return MediatorResult.Error(ex)
                } catch (ex: HttpException) {
                    return MediatorResult.Error(ex)
                }
            }
            else -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private suspend fun loadCities(): List<City> {
        val response = weatherService.getCitiesFile()
        val decodedString = citiesParser.decompressGZip(response)
        return citiesParser.parseJson(decodedString)
    }
}