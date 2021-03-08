package ru.mrfiring.shiftweatherapp.data

import androidx.paging.*
import androidx.paging.rxjava2.flowable
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.DomainCity
import ru.mrfiring.shiftweatherapp.domain.asDomainObject

@ExperimentalCoroutinesApi
class CitiesRepositoryImpl @ExperimentalPagingApi constructor(
    private val citiesDao: CitiesDao,
    private val cityMediator: CityMediator,
): CitiesRepository {

    @ExperimentalPagingApi
    override fun getCitiesFlowable(): Flowable<PagingData<DomainCity>> {
        val pagingSourceFactory = {
            citiesDao.getCities()
        }
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = cityMediator,

            ).flowable.map {
            it.map { item ->
                item.asDomainObject()
            }
        }
    }
}