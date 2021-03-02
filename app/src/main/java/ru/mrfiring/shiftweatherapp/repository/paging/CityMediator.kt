package ru.mrfiring.shiftweatherapp.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.mrfiring.shiftweatherapp.repository.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.repository.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.repository.network.*
import java.io.IOException

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
                    withContext(Dispatchers.IO){
                        val citiesList: List<City> = loadCities()
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
        val parser = CitiesParser()
        val decodedString = parser.decompressGZip(response)
        return parser.parseJson(decodedString)
    }
}