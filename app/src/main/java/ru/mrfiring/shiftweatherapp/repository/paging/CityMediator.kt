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
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.repository.network.asDatabaseObject
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class CityMediator(
    val weatherService: OpenWeatherService,
    val database: WeatherDatabase
): RemoteMediator<Int, DatabaseCity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DatabaseCity>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when(pageKeyData){
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try{
            val response = weatherService.getCitiesFile()
            val parser = CitiesParser()
            val decodedString = parser.decompressGZip(response)
            val citiesList = parser.parseJson(decodedString)
            val isEndOfList = citiesList.isNotEmpty()

            database.withTransaction {
                val prevKey = if(page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if(isEndOfList) null else page + 1
                val keys = citiesList.map {
                    RemoteKey(it.id.toString(), prevKey ?: 0, nextKey ?: 0)
                }
                database.remoteKeyDao.insertAll(keys)
                database.citiesDao.insertCities(citiesList.map {
                    it.asDatabaseObject()
                })
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)

        }catch (ex: IOException){
            return MediatorResult.Error(ex)
        }catch (ex: HttpException){
            return MediatorResult.Error(ex)
        }
    }

    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, DatabaseCity>): Any?{
        return when(loadType){
            LoadType.REFRESH -> {
                val remoteKey = getClosestRemoteKey(state)
                remoteKey?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state) ?: throw InvalidObjectException(
                    "Remote key should not be null"
                )
                remoteKey.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey(state) ?: throw InvalidObjectException(
                    "Remote key should not be null"
                )
                remoteKey.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                //remoteKey.prevKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, DatabaseCity>): RemoteKey?{
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let {city ->
                database.remoteKeyDao.getRemoteKeyById(city.id.toString())
            }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, DatabaseCity>): RemoteKey?{
        return state.pages
            .lastOrNull {it.data.isNotEmpty()}
            ?.data?.lastOrNull()
            ?.let {city ->
                database.remoteKeyDao.getRemoteKeyById(city.id.toString())
            }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, DatabaseCity>): RemoteKey?{
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeyDao.getRemoteKeyById(id.toString())
            }
        }



    }
    companion object {
        private const val DEFAULT_PAGE_INDEX = 0
    }
}