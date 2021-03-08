package ru.mrfiring.shiftweatherapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.mrfiring.shiftweatherapp.data.CitiesParser
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.data.network.asDatabaseObject

@ExperimentalPagingApi
class CityMediator(
    private val weatherService: OpenWeatherService,
    private val citiesDao: CitiesDao,
    private val citiesParser: CitiesParser
) : RxRemoteMediator<Int, DatabaseCity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, DatabaseCity>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .flatMap {
                when (it) {
                    LoadType.REFRESH -> {
                        if (citiesDao.getCountOfCities() > 0) {
                            return@flatMap Single.just(MediatorResult.Success(endOfPaginationReached = true))
                        }

                        weatherService.getCitiesFile()
                            .flatMap { response ->
                                citiesParser.decompressGZip(response)
                            }
                            .flatMap { json ->
                                citiesParser.parseJson(json)
                            }
                            .map { cities ->
                                cities.map { city -> city.asDatabaseObject() }
                            }
                            .map { dbCities ->
                                citiesDao.insertCities(dbCities)
                            }
                            .map<MediatorResult> { result ->
                                MediatorResult.Success(endOfPaginationReached = false)

                            }
                            .onErrorReturn { err ->
                                MediatorResult.Error(err)
                            }
                    }
                    else -> {
                        Single.just(MediatorResult.Success(endOfPaginationReached = true))
                    }
                }
            }
            .onErrorReturn {
                MediatorResult.Error(it)
            }
    }

}