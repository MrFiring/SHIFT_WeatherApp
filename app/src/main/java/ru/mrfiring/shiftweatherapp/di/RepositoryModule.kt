package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.repository.WeatherRepository
import ru.mrfiring.shiftweatherapp.repository.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.repository.paging.CityMediator

@ExperimentalPagingApi
val repositoryModule = module {

    fun provideWeatherRepository(database: WeatherDatabase,
                                 networkService: OpenWeatherService,
                                 cityMediator: CityMediator
    ): WeatherRepository {
        return WeatherRepository(database, networkService, cityMediator)
    }

    single {provideWeatherRepository(get(), get(), get())}
}